package stopwatch

import chisel3._


// 产生ticks
class tickGenerator extends Module {
    val io = IO(new Bundle {
      val tick4  = Output(Bool())
      val tick40 = Output(Bool())
    })

    val tickCounterReg4  = RegInit(0.U(8.W))
    val tickCounterReg40 = RegInit(0.U(8.W))
    val t4  = tickCounterReg4 === 3.U           // 秒表个位数每4个周期发一个tick
    val t40 = tickCounterReg40 === 39.U         // 秒表十位数每40个周期发一个tick
    io.tick4  := t4
    io.tick40 := t40
    tickCounterReg4 := Mux(t4, 0.U, tickCounterReg4 + 1.U)
    tickCounterReg40 := Mux(t40, 0.U, tickCounterReg40 + 1.U)
}

// 根据tick生成秒表时钟序列
class stopWatch extends Module {
    val io = IO(new Bundle {
      val dout_low = Output(UInt(4.W))
      val dout_high = Output(UInt(4.W))
    })

    val tickGen         = Module(new tickGenerator())
    val highFrequCntReg = RegInit(0.U(4.W))
    val lowFrequCntReg  = RegInit(0.U(4.W))
    
    io.dout_high  := lowFrequCntReg
    io.dout_low   := highFrequCntReg

    when (lowFrequCntReg === 5.U) {             // 秒表十位数计数达到最大值5     
      when (tickGen.io.tick40) {
        lowFrequCntReg := 0.U                   // 在下一个tick到来后清零
      }
    }.otherwise {  
      when (tickGen.io.tick40) {                // 否则在每一个tick自增1
        lowFrequCntReg := lowFrequCntReg + 1.U
      }
    }

    when (highFrequCntReg === 9.U) {            // 秒表个位数计数达到最大值9
      when (tickGen.io.tick4) {                 
        highFrequCntReg := 0.U                  // 在下一个tick到来后清零
      }
    }.otherwise {
      when (tickGen.io.tick4) {
        highFrequCntReg := highFrequCntReg + 1.U
      }
    }
}

// 拼接秒表的十位数和个位数得到最终输出
class stopWatchTop extends Module {
    val io = IO(new Bundle {
      val ss = Output(UInt(8.W))
    })
    
    val SW  = Module(new stopWatch())
    io.ss  := SW.io.dout_high ## SW.io.dout_low
}

object MyStopWatch extends App {
    emitVerilog(new stopWatchTop(), Array("--target-dir", "generated"))
}
