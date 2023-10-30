package PE

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._
import org.scalatest.matchers.should.Matchers

class multiplierTest extends AnyFlatSpec with ChiselScalatestTester {
    "multiplier" should "pass" in {
        test(new Multiplier) { dut =>
            dut.input.multiplier.poke(90.U)
            dut.input.multiplicand.poke(100.U)
            dut.clock.step()
            dut.clock.step()
            dut.clock.step()

            println("product is: " + dut.output.product.peek().toString)
        }
    }
}