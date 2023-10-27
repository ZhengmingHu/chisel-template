package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

//todo decoupledIO
class beInputBundle(val w:Int) extends Bundle{
    val multiplier = Input(UInt(w.W))
}

class beOutputBundle(val w:Int) extends Bundle{
    val X2   = Output(UInt(w.W))
    val inv  = Output(UInt(w.W))
    val set0 = Output(UInt(w.W))
}

class booth2Encoder extends Module{
    val input  = IO(new beInputBundle(8))
    val output = IO(new beOutputBundle(4))

    val multiplier2 = Wire(UInt(9.W))
    val bits        = Wire(Vec(4, UInt(3.W)))

    val X2Bools     = VecInit(Seq.fill(4)(false.B))
    val invBools    = VecInit(Seq.fill(4)(false.B))
    val set0Bools   = VecInit(Seq.fill(4)(false.B))
    
    
    
    multiplier2 := Cat(input.multiplier, 0.U(1.W))
    bits(0)     := multiplier2(2,0)
    bits(1)     := multiplier2(4,2)
    bits(2)     := multiplier2(6,4)
    bits(3)     := multiplier2(8,6)

    for (i <- 0 until 4){
        X2Bools(i)   := (~(bits(i)(0)^bits(i)(1))).asBool
        invBools(i)  := bits(i)(2).asBool
        set0Bools(i) := (bits(i).andR).asUInt | ((~bits(i)).andR).asBool

    }
    
    output.X2   := X2Bools.asUInt
    output.inv  := invBools.asUInt
    output.set0 := set0Bools.asUInt

}
