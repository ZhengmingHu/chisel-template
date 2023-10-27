package PE

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._
import org.scalatest.matchers.should.Matchers

class booth2EncoderTest extends AnyFlatSpec with ChiselScalatestTester {
    "booth2Encoder" should "pass" in {
        test(new booth2Encoder) { dut =>
            dut.input.multiplier.poke(15.U)
            dut.clock.step()
            println("multiplier is: " + dut.input.multiplier.peek().toString)
            println("X2 is: " + dut.output.X2.peek().toString)
            println("inv is: " + dut.output.inv.peek().toString)
            println("set0 is: " + dut.output.set0.peek().toString)    
        }
    }
}