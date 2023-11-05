package SA

import chisel3._
import chisel3.util.Cat
import chisel3.util.Fill
import MUL._



class PEControl extends Bundle {
  val done  = Bool()
}

class PE(val IN_WIDTH: Int, val C_WIDTH: Int) extends Module {
  val io = IO(new Bundle {
    val in_control = Input(new PEControl)
    val in_a = Input(UInt(IN_WIDTH.W))
    val in_b = Input(UInt(IN_WIDTH.W))
    val in_c = Input(UInt(C_WIDTH.W))

    val out_control = Output(new PEControl)
    val out_a = Output(UInt(IN_WIDTH.W))
    val out_b = Output(UInt(IN_WIDTH.W))
    val out_c = Output(UInt(C_WIDTH.W))
  })

  val   mul = Module(new Multiplier(IN_WIDTH, 2*IN_WIDTH))
  val   rca   = Module(new RCA(C_WIDTH))

  val a_reg = RegInit(0.U(IN_WIDTH.W))
  val b_reg = RegInit(0.U(IN_WIDTH.W))
  val c_reg = RegInit(0.U(C_WIDTH.W))

  a_reg := io.in_a
  b_reg := io.in_b

  mul.input.multiplicand := io.in_a
  mul.input.multiplier   := io.in_b
  rca.input.a_in         := Cat(Fill((C_WIDTH-2*IN_WIDTH), mul.output.product(2*IN_WIDTH-1)), mul.output.product)
  rca.input.b_in         := c_reg
  rca.input.c_in         := 0.U(C_WIDTH.W)

  c_reg := Mux(io.in_control.done === true.B, io.in_c, rca.output.S)

  io.out_a := a_reg
  io.out_b := b_reg
  io.out_c := c_reg

  io.out_control := io.in_control
}