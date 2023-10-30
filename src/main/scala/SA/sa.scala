package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

class tickGenerator extends Module {
    val io = IO(new Bundle {
        //val cntReset = Input(Bool())
        val tick11   = Output(Bool())
        val tick15   = Output(Bool())
    })

    val tickCounterReg11  = RegInit(0.U(8.W))
    val tickCounterReg15  = RegInit(0.U(8.W))
    val t11  = tickCounterReg11 === 10.U
    val t15  = tickCounterReg15 === 14.U
    io.tick11  := t11
    io.tick15  := t15
    tickCounterReg11 := Mux(t11, 0.U, tickCounterReg11 + 1.U)
    tickCounterReg15 := Mux(t15, 0.U, tickCounterReg15 + 1.U)   //TODO output_done
}

class SystolicArray extends Module {
     val io = IO(new Bundle{
        val in_a      = Input(Vec(4, UInt(8.W)))
        val in_b      = Input(Vec(4, UInt(8.W)))

        val out_c     = Output(Vec(4, UInt(16.W)))
        //val out_done  = Output(Bool())
    })

    def pipeReg(input: UInt): UInt = {
        val register = RegInit(0.U(16.W))
        register := input
        register
    }

    val macUnits       = Seq.fill(4)(Seq.fill(4)(Module(new macUnit)))
    val tickGen        = Module(new tickGenerator)
    //val regHorizontal  = RegInit(VecInit(Seq.fill(4)(VecInit(Seq.fill(3)(0.U(16.W))))))
    //val regVertical    = RegInit(VecInit(Seq.fill(3)(VecInit(Seq.fill(4)(0.U(16.W))))))
    val tick11         = WireInit(false.B)
    val tick15         = WireInit(false.B)
    val cal_done       = RegInit(false.B)

    tick11            := tickGen.io.tick11
    when(tick11){
        cal_done := true.B
    }
    


    for (i <- 0 until 4) {
        for (j <- 0 until 4){
            macUnits(i)(j).io.done     := cal_done
            if((i == 0) && (j == 0)){
                macUnits(i)(j).io.in_a := io.in_a(i)
                macUnits(i)(j).io.in_b := io.in_b(j) 
                macUnits(i)(j).io.in_c := 0.U(16.W)       
            }
            else if(j == 0){
                macUnits(i)(j).io.in_a := io.in_a(i)
                macUnits(i)(j).io.in_b := pipeReg(macUnits(i-1)(j).io.out_b)
                macUnits(i)(j).io.in_c := macUnits(i-1)(j).io.out_c
            }
            else if(i == 0){
                macUnits(i)(j).io.in_a := pipeReg(macUnits(i)(j-1).io.out_a)
                macUnits(i)(j).io.in_b := io.in_b(j)
                macUnits(i)(j).io.in_c := 0.U(16.W)   
            }
            else if(i == 3){
                macUnits(i)(j).io.in_a := pipeReg(macUnits(i)(j-1).io.out_a)
                macUnits(i)(j).io.in_b := pipeReg(macUnits(i-1)(j).io.out_b)
                macUnits(i)(j).io.in_c := macUnits(i-1)(j).io.out_c
            }
            else {
                macUnits(i)(j).io.in_a := pipeReg(macUnits(i)(j-1).io.out_a)
                macUnits(i)(j).io.in_b := pipeReg(macUnits(i-1)(j).io.out_b)
                macUnits(i)(j).io.in_c := macUnits(i-1)(j).io.out_c
            }
        }
    }
    for (j <- 0 until 4)
        io.out_c(j)            := macUnits(3)(j).io.out_c  
} 