package PE

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
    val pp0_out = Output(UInt(w.W))
    val pp1_out = Output(UInt(w.W))
    val pp2_out = Output(UInt(w.W))
    val pp3_out = Output(UInt(w.W))
    val sig_out = Output(UInt(w.W))
    val X2      = Output(UInt(4.W))
    val inv     = Output(UInt(4.W))
    val set0    = Output(UInt(4.W))
    
    val S_4_2   = Output(UInt(16.W))
    val C_4_2   = Output(UInt(16.W))
}

class Multiplier extends Module{
    val input  = IO(new MultiplierInputBundle(8))
    val output = IO(new MultiplierOutputBundle(16))

    val booth2_encoder   = Module(new booth2Encoder())
    val pp_generator     = Module(new ppGenerator())
    val pp_compressor4_2 = Module(new pp_compressor4_2())
    val pp_compressor3_2 = Module(new pp_compressor3_2())
    val rca              = Module(new RCA())

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
    rca.input.c_in                      := 0.U(16.W)

    output.product                      := rca.output.S
    output.pp0_out                      := pp_generator.outputData.pp0_out
    output.pp1_out                      := pp_generator.outputData.pp1_out    
    output.pp2_out                      := pp_generator.outputData.pp2_out
    output.pp3_out                      := pp_generator.outputData.pp3_out
    output.sig_out                      := pp_generator.outputData.sig_out

    output.X2                           := booth2_encoder.output.X2
    output.inv                          := booth2_encoder.output.inv
    output.set0                         := booth2_encoder.output.set0

    output.S_4_2                        := pp_compressor4_2.output.S
    output.C_4_2                        := pp_compressor4_2.output.C

}