package SA

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._

class GlobalCounterTest extends AnyFlatSpec with ChiselScalatestTester {
    "GlobalCounter" should "pass" in {
        test(new GlobalCounter(10))
            .withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
            dut.io.start.poke(true.B)
            dut.clock.step(1)
            dut.io.start.poke(false.B)
            for (i <- 0 until 100){
              dut.clock.step(1)
            }
        }
    }
}