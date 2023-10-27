package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

//todo DecoupledIO
class pgInputDataBundle(val w:Int) extends Bundle{
    val multiplicand = Input(UInt(w.W))
}

class pgInputCtrlBundle(val w:Int) extends Bundle{
    val X2   = Input(UInt(w.W))
    val inv  = Input(UInt(w.W))
    val Set0 = Input(UInt(w.W))
}

class pgOutputDataBundle(val w:Int) extends Bundle{
    val pp0_out = Output(UInt(w.W))
    val pp1_out = Output(UInt(w.W))
    val pp2_out = Output(UInt(w.W))
    val pp3_out = Output(UInt(w.W))
    val sig_out = Output(UInt(w.W))
}

class ppGenerator extends Module {
    val inputData  = IO(new pgInputDataBundle(8))
    val inputCtrl  = IO(new pgInputCtrlBundle(4))
    val outputData = IO(new pgOutputDataBundle(16))

    val E          = Wire(Vec(4, Bool()))
    val E_inv      = Wire(Vec(4, Bool()))

    val pp_X2      = Wire(Vec(4, UInt(9.W)))
    val pp_set     = Wire(Vec(4, UInt(9.W)))
    val pp_inv     = Wire(Vec(4, UInt(9.W)))
    val pp_temp    = Wire(Vec(4, UInt(16.W)))

    val pp0        = RegInit(0.U(16.W)) 
    val pp1        = RegInit(0.U(16.W))
    val pp2        = RegInit(0.U(16.W))
    val pp3        = RegInit(0.U(16.W))
    val sign_com   = RegInit(0.U(16.W))

    for (i <- 0 until 4){
        pp_X2(i)  := Mux(inputCtrl.X2(i), Cat(inputData.multiplicand, 0.U(1.W)), 
                        Cat(inputData.multiplicand(7), inputData.multiplicand))
        pp_set(i) := Mux(inputCtrl.Set0(i), 0.U(9.W), pp_X2(i))
        pp_inv(i) := Mux(inputCtrl.inv(i), ~(pp_set(i)), pp_set(i))             //add 1 in next pipeline
        E_inv(i)  := Mux(inputCtrl.Set0(i), inputCtrl.inv(i).asBool, (inputCtrl.inv(i)^inputData.multiplicand(7)).asBool)
        E(i)      := ~E_inv(i)
        
        if (i == 0){
            pp_temp(i) := Cat(0.U(4.W), E(0), E_inv(0).asUInt, E_inv(0).asUInt, pp_inv(0))
        }
        else {
            pp_temp(i) := Cat(0.U(5.W), 1.U(1.W), E(i).asUInt, pp_inv(i))
        }
    }

    pp0      := pp_temp(0)
    pp1      := Cat(pp_temp(1)(13,0), 0.U(1.W), inputCtrl.inv(0))
    pp2      := Cat(pp_temp(2)(11,0), 0.U(1.W), inputCtrl.inv(1), 0.U(2.W))
    pp3      := Cat(pp_temp(3)( 9,0), 0.U(1.W), inputCtrl.inv(2), 0.U(4.W))
    sign_com := Cat(0.U(9.W), inputCtrl.inv(3), 0.U(6.W))

    outputData.pp0_out  := pp0
    outputData.pp1_out  := pp1
    outputData.pp2_out  := pp2
    outputData.pp3_out  := pp3
    outputData.sig_out  := sign_com
}