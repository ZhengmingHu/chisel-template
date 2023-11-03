package MUL

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

//todo DecoupledIO
class MultiplierInputBundle(val w:Int) extends Bundle{
    val multiplicand = Input(UInt(w.W))
    val multiplier   = Input(UInt(w.W))
}

class MultiplierOutputBundle(val w:Int) extends Bundle{
    val product = Output(UInt(w.W))
}

class Multiplier(val IN_WIDTH: Int, val C_WIDTH: Int) extends Module{
    val input  = IO(new MultiplierInputBundle(IN_WIDTH))
    val output = IO(new MultiplierOutputBundle(C_WIDTH))

    val booth2_encoder   = Module(new booth2Encoder(IN_WIDTH))
    val pp_generator     = Module(new ppGenerator(IN_WIDTH, C_WIDTH))
    val pp_compressor4_2 = Module(new pp_compressor4_2(C_WIDTH))
    val pp_compressor3_2 = Module(new pp_compressor3_2(C_WIDTH))
    val rca              = Module(new RCA(C_WIDTH))

    booth2_encoder.input.multiplier := input.multiplier

    pp_generator.inputData.multiplicand := input.multiplicand
    pp_generator.inputCtrl.X2           := booth2_encoder.output.X2
    pp_generator.inputCtrl.inv          := booth2_encoder.output.inv
    pp_generator.inputCtrl.Set0         := booth2_encoder.output.set0

    pp_compressor4_2.input.pp0_in       := pp_generator.outputData.pp0_out
    pp_compressor4_2.input.pp1_in       := pp_generator.outputData.pp1_out
    pp_compressor4_2.input.pp2_in       := pp_generator.outputData.pp2_out
    pp_compressor4_2.input.pp3_in       := pp_generator.outputData.pp3_out

    pp_compressor3_2.input.pp0_in       := pp_compressor4_2.output.S
    pp_compressor3_2.input.pp1_in       := pp_compressor4_2.output.C
    pp_compressor3_2.input.pp2_in       := pp_generator.outputData.sig_out

    rca.input.a_in                      := pp_compressor3_2.output.S
    rca.input.b_in                      := pp_compressor3_2.output.C
    rca.input.c_in                      := 0.U(C_WIDTH.W)

    output.product                      := rca.output.S


}