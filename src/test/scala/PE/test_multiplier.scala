package PE

package PE

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
            println("PP0 is: " + dut.output.pp0_out.peek().toString)
            println("PP1 is: " + dut.output.pp1_out.peek().toString)
            println("PP2 is: " + dut.output.pp2_out.peek().toString)
            println("PP3 is: " + dut.output.pp3_out.peek().toString)
            println("SIG is: " + dut.output.sig_out.peek().toString)

            println("X2 is: " + dut.output.X2.peek().toString)
            println("INV is: " + dut.output.inv.peek().toString)
            println("SET0 is: " + dut.output.set0.peek().toString)

            println("S_4_2 is: " + dut.output.S_4_2.peek().toString)
            println("C_4_2 is: " + dut.output.C_4_2.peek().toString)

            println("product is: " + dut.output.product.peek().toString)
        }
    }
}