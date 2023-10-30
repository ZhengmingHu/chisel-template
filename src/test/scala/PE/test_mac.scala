package PE

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._
import org.scalatest.matchers.should.Matchers

class macTest extends AnyFlatSpec with ChiselScalatestTester {
    "mac" should "pass" in {
        test(new macUnit) { dut =>
            dut.io.in_a.poke(90.U)
            dut.io.in_b.poke(100.U)
            dut.io.in_c.poke(0.U)
            dut.io.done.poke(0.U)

            dut.clock.step()
            

            dut.io.in_a.poke(9.U)
            dut.io.in_b.poke(1.U)
            dut.io.in_c.poke(0.U)
            dut.io.done.poke(0.U)

            dut.clock.step()

            dut.io.in_a.poke(9.U)
            dut.io.in_b.poke(1.U)
            dut.io.in_c.poke(0.U)
            dut.io.done.poke(0.U)

            dut.clock.step()
            dut.clock.step()
            

            println("mac_result is: " + dut.io.out_c.peek().toString)
        }
    }
}