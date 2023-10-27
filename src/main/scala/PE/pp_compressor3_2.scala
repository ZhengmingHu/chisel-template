package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

class pp_compressor3_2InputBundle(val w:Int) extends Bundle{
    val pp0_in  = Input(UInt(w.W))
    val pp1_in  = Input(UInt(w.W))
    val pp2_in  = Input(UInt(w.W))
}

class pp_compressor3_2OutputBundle(val w:Int) extends Bundle{
    val S    = Output(UInt(w.W))
    val C    = Output(UInt(w.W))
}

class pp_compressor3_2 extends Module{
    val input  = IO(new pp_compressor3_2InputBundle(16))
    val output = IO(new pp_compressor3_2OutputBundle(16))

    val g_comb = Wire(Vec(16, Bool()))
    val p_comb = Wire(Vec(16, Bool()))
    val s_comb = Wire(Vec(16, Bool()))
    val c_comb = Wire(Vec(16, Bool()))
    
    for (i <- 0 until 16){
        g_comb(i) := (input.pp0_in(i) & input.pp1_in(i)).asBool
        p_comb(i) := (input.pp0_in(i) ^ input.pp1_in(i)).asBool
        s_comb(i) := p_comb(i) ^ input.pp2_in(i).asBool
        c_comb(i) := input.pp2_in(i).asBool & p_comb(i) | g_comb(i) 
    }

    output.S := s_comb.asUInt
    output.C := Cat((c_comb.asUInt)(14,0), 0.U(1.W))

}