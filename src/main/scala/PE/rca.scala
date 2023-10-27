package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

class rcaInputBundle(val w:Int) extends Bundle{
    val a_in  = Input(UInt(w.W))
    val b_in  = Input(UInt(w.W))
    val c_in   = Input(UInt(1.W))
}

class rcaOutputBundle(val w:Int) extends Bundle{
    val S    = Output(UInt(w.W))
    val C    = Output(UInt(w.W))
}

class RCA extends Module{
    val input  = IO(new rcaInputBundle(16))
    val output = IO(new rcaOutputBundle(16))

    val g_comb = Wire(Vec(16, Bool()))
    val p_comb = Wire(Vec(16, Bool()))
    val s_comb = Wire(Vec(16, Bool()))
    val c_comb = Wire(Vec(17, Bool()))

    c_comb(0) := input.c_in.asBool

    for (i <- 0 until 16){
        g_comb(i)   := (input.a_in(i) & input.b_in(i)).asBool
        p_comb(i)   := (input.a_in(i) ^ input.b_in(i)).asBool
        s_comb(i)   := p_comb(i) ^ c_comb(i)
        c_comb(i+1) := c_comb(i) & p_comb(i) | g_comb(i) 
    }

    output.S := s_comb.asUInt
    output.C := c_comb(16).asUInt

}