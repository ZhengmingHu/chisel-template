package PE

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3.experimental.BundleLiterals._
import org.scalatest.matchers.should.Matchers

class ppGeneratorTest extends AnyFlatSpec with ChiselScalatestTester {
    "ppGenerator" should "pass" in {
        test(new ppGenerator) { dut =>
            dut.inputData.multiplicand.poke(15.U)
            dut.inputCtrl.X2.poke(1.U)
            dut.inputCtrl.inv.poke(2.U)
            dut.inputCtrl.Set0.poke(3.U)
            dut.clock.step()
            dut.clock.step()
            println("pp1 is: " + dut.outputData.pp1_out.peek().toString)
           
        }
    }
}

// class WaveformTest extends AnyFlatSpec with ChiselScalatestTester {
//     "Waveform" should "pass" in {
//         test(new ppGenerator)
//             .withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
//             for (i <- 0 until 500){
//                 dut.inputData.multiplicand.poke(15.U)
//                 dut.inputCtrl.X2.poke(1.U)
//                 dut.inputCtrl.inv.poke(2.U)
//                 dut.inputCtrl.Set0.poke(3.U)
//                 dut.clock.step(1)
//             }
//         }
//     }
// }


