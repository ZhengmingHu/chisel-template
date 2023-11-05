package SA

import chisel3._
import chisel3.util._

import chisel3._
import chisel3.util._

class GlobalCounter(val maxCount:Int) extends Module {
  val io = IO(new Bundle {
    val start = Input(Bool())
    val tick = Output(Bool())
  })

  val count = RegInit(0.U(4.W))
  val tick = count === maxCount.asUInt

  val enable = RegInit(false.B)
  when(io.start) {
    enable := true.B
  }.elsewhen(tick) {
    enable := false.B
  }

  when(enable) {
    when(count =/= maxCount.asUInt) {
      count := count + 1.U
    }.otherwise {
      count := 0.U
    }
  }

  io.tick := tick
}

class Controller(val SA_ROWS: Int, val SA_COLS: Int) extends Module {
  val io = IO(new Bundle {
    val start    = Input(Bool())
    val cal_done = Output(Bool())
    val out_done = Output(Bool())
  })

  // idle：等待指令
  // compute：执行矩阵乘法
  // completed：运算执行结束，将每个PE里的结果流出
  val idle :: compute :: completed :: Nil = Enum(3)
  val state = RegInit(idle)
 
  // 初始化cal_done和out_done
  val cal_done_r   = RegInit(false.B)
  val out_done_r   = RegInit(false.B)

  // 例化产生cal_done的全局计数器，标志计算完成，可以开始输出结果
  val cal_gc       = Module(new GlobalCounter(3*SA_ROWS-2)) // todo ROWs和COLs取最大值
  cal_gc.io.start := io.start 
  when(cal_gc.io.tick) {
    cal_done_r     := true.B
  }
  io.cal_done     := cal_done_r

  // 例化产生out_done的全局计数器，标志输出完成，可以开始输出结果
  val out_gc       = Module(new GlobalCounter(SA_COLS-1))
  out_gc.io.start := cal_gc.io.tick
  when(out_gc.io.tick) {
    out_done_r     := true.B
  }
  io.out_done     := out_done_r

  // FSM
  when(state === idle) {
    when(io.start) {
      state := compute
    }
  }.elsewhen(state === compute) {
    when(cal_done_r) {
      state := completed
    }
  }.elsewhen(state === completed) {
    when(out_done_r) {
      state := idle
      cal_done_r := false.B
      out_done_r := false.B
    }
  }
}