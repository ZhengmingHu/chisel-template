package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

class macUnit extends Module {
    val io = IO(new Bundle{
        val done  = Input(Bool())
        val in_a  = Input(UInt(8.W))
        val in_b  = Input(UInt(8.W))
        val in_c  = Input(UInt(16.W))
        val out_c = Output(UInt(16.W))
    })
    val mul   = Module(new Multiplier())
    val rca   = Module(new RCA()) 

    val c_reg = RegInit(0.U(16.W))

    mul.input.multiplicand := io.in_a
    mul.input.multiplier   := io.in_b
    rca.input.a_in                         := mul.output.product
    rca.input.b_in                         := c_reg
    rca.input.c_in                         := 0.U(16.W)
    
    c_reg    := Mux(io.done === true.B, io.in_c, rca.output.S)
    io.out_c := c_reg
}