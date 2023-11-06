// package PE

// import chisel3._
// import chiseltest._
// import org.scalatest.flatspec.AnyFlatSpec
// import chisel3.experimental.BundleLiterals._
// import org.scalatest.matchers.should.Matchers

// // class csa4_2Test extends AnyFlatSpec with ChiselScalatestTester {
// //     "csa4_2" should "pass" in {
// //         test(new CSA4_2) { dut =>
// //             dut.input.i1.poke(1.U)
// //             dut.input.i2.poke(0.U)
// //             dut.input.i3.poke(1.U)
// //             dut.input.i4.poke(0.U)
// //             dut.input.cin.poke((0.U))
// //             dut.clock.step()
// //             dut.clock.step()
// //             println("S is: " + dut.output.S.peek().toString)
// //             println("C is: " + dut.output.C.peek().toString)
// //             println("cout is: " + dut.output.cout.peek().toString)
           
// //         }
// //     }
// // }

// class csa4_2Test extends AnyFlatSpec with ChiselScalatestTester {
//     "compressor4_2" should "pass" in {
//         test(new pp_compressor4_2) { dut =>
//             dut.input.pp0_in.poke(2.U)
//             dut.input.pp1_in.poke(4.U)
//             dut.input.pp2_in.poke(2.U)
//             dut.input.pp3_in.poke(4.U)
//             dut.clock.step()
//             dut.clock.step()
//             println("S_csa4_2 is: " + dut.output.S.peek().toString)
//             println("C_csa4_2 is: " + dut.output.C.peek().toString)
//         }
//     }
// }