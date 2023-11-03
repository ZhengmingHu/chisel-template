//package SA
//
//import chisel3._
//import chisel3.util._
//
//class Controller extends Module {
//  val io = IO(new Bundle {
//    val start = Input(Bool())
//    val done = Output(Bool())
//  })
//
//  // idle：等待指令
//  // compute：执行矩阵乘法
//  // completed：运算执行结束，将每个PE里的结果流出
//  val idle :: compute :: completed :: Nil = Enum(3)
//  val state = RegInit(idle)
//
//
//  io.done := false.B
//
//  // FSM
//  when(state === idle) {
//
//    when(io.start) {
//      state := running
//    }
//  }.elsewhen(state === running) {
//    when(io.start) {
//      state := done
//    }
//  }.elsewhen(state === done) {
//    state := idle
//    io.done := true.B
//  }
//}