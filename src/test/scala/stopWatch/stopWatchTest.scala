package stopwatch

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._

class WaveformTest extends AnyFlatSpec with ChiselScalatestTester {
    "Waveform" should "pass" in {
        test(new stopWatchTop)
            .withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
            for (i <- 0 until 500){
              dut.clock.step(1)
            }
        }
    }
}
