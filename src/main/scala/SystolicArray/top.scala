package top

import chisel3._
import chisel3.util._
import chisel3.stage._

import SA._


class top extends Module {
  val io = IO(new Bundle {
    val in_start   = Input(Bool())
    val in_control = Output(Vec(2, new PEControl))
    val in_a = Input(Vec(2, UInt(8.W)))
    val in_b = Input(Vec(2, UInt(8.W)))
    val in_c = Input(Vec(2, UInt(32.W)))

    val out_control = Output(Vec(2, new PEControl))
    val out_a = Output(Vec(2, UInt(8.W)))
    val out_b = Output(Vec(2, UInt(8.W)))
    val out_c = Output(Vec(2, UInt(32.W)))
  })

  val sa = Module(new SA(8,32,2,2))
  val controller = Module(new Controller(2, 2))


  sa.io.in_a := io.in_a
  sa.io.in_b := io.in_b
  sa.io.in_c := io.in_c
  sa.io.in_control := io.in_control
  
  controller.io.start := io.in_start
  for (c <- 0 until 2) {
    io.in_control(c).done := controller.io.cal_done
  }
  

  io.out_a := sa.io.out_a
  io.out_b := sa.io.out_b
  io.out_c := sa.io.out_c
  io.out_control := sa.io.out_control
}

