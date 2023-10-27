package alu

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._
import org.scalatest.matchers.should.Matchers

class aluTest extends AnyFlatSpec with ChiselScalatestTester {
    "ALU" should "pass" in {
        test(new ALU) { dut =>
            dut.io.a.poke(2.U)
            dut.io.b.poke(3.U)
            dut.io.fn.poke(0.U)
            dut.clock.step()
            println("Result is: " + dut.io.y.peek().toString)
        }
    }
}

