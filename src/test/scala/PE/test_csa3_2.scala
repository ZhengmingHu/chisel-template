// package PE

// import chisel3._
// import chiseltest._
// import org.scalatest.flatspec.AnyFlatSpec
// import chisel3.experimental.BundleLiterals._
// import org.scalatest.matchers.should.Matchers

// class csa3_2Test extends AnyFlatSpec with ChiselScalatestTester {
//     "compressor3_2" should "pass" in {
//         test(new pp_compressor3_2) { dut =>
//             dut.input.pp0_in.poke(4.U)
//             dut.input.pp1_in.poke(4.U)
//             dut.input.pp2_in.poke(3.U)
//             dut.clock.step()
//             dut.clock.step()
//             println("S_csa3_2 is: " + dut.output.S.peek().toString)
//             println("C_csa3_2 is: " + dut.output.C.peek().toString)
//         }
//     }
// }