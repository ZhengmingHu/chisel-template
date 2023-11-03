package MUL

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

//todo DecoupledIO
class csa4_2InputBundle(val w:Int) extends Bundle{
    val i1  = Input(UInt(w.W))
    val i2  = Input(UInt(w.W))
    val i3  = Input(UInt(w.W))
    val i4  = Input(UInt(w.W))
    val cin = Input(UInt(w.W))
}

class csa4_2OutputBundle(val w:Int) extends Bundle{
    val S    = Output(UInt(w.W))
    val C    = Output(UInt(w.W))
    val cout = Output(UInt(w.W))
}

class pp_compressor4_2InputBundle(val w:Int) extends Bundle{
    val pp0_in  = Input(UInt(w.W))
    val pp1_in  = Input(UInt(w.W))
    val pp2_in  = Input(UInt(w.W))
    val pp3_in  = Input(UInt(w.W))
}

class pp_compressor4_2OutputBundle(val w:Int) extends Bundle{
    val C  = Output(UInt(w.W))
    val S  = Output(UInt(w.W))
}

class CSA4_2 extends Module{
    val input  = IO(new csa4_2InputBundle(1))
    val output = IO(new csa4_2OutputBundle(1))

    output.S    := input.i1 ^ input.i2 ^ input.i3 ^ input.i4 ^ input.cin
    output.C    := (input.i1 & ~(input.i1 ^ input.i2 ^ input.i3 ^ input.i4)) |
                   (input.cin & (input.i1 ^ input.i2 ^ input.i3 ^ input.i4))
    output.cout := (input.i4 & input.i3) | (input.i3 & input.i2) | (input.i4 & input.i2)
}

class pp_compressor4_2(val C_WIDTH: Int) extends Module{
    val input  = IO(new pp_compressor4_2InputBundle(C_WIDTH))
    val output = IO(new pp_compressor4_2OutputBundle(C_WIDTH))

    val c_temp = Wire(Vec(C_WIDTH+1, Bool()))
    c_temp(0) := false.B

    val s_comb     = Wire(Vec(C_WIDTH, Bool()))
    val c_comb     = Wire(Vec(C_WIDTH, Bool()))
    val c_comb_int = Wire(UInt(C_WIDTH.W))

    for (i <- 0 until C_WIDTH){
        val csa4_2 = Module(new CSA4_2())
        csa4_2.input.i1  := input.pp0_in(i)
        csa4_2.input.i2  := input.pp1_in(i)
        csa4_2.input.i3  := input.pp2_in(i)
        csa4_2.input.i4  := input.pp3_in(i)
        csa4_2.input.cin := c_temp(i).asUInt

        s_comb(i)        := csa4_2.output.S.asBool
        c_comb(i)        := csa4_2.output.C.asBool
        c_temp(i+1)      := csa4_2.output.cout.asBool
    }

    output.S   := s_comb.asUInt
    c_comb_int := c_comb.asUInt
    output.C   := Cat(c_comb_int(C_WIDTH-2,0), 0.U(1.W))
}