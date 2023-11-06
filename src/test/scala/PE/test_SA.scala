// package PE


// import chisel3._
// import chiseltest._
// import org.scalatest.flatspec.AnyFlatSpec
// import chisel3.experimental.BundleLiterals._
// import org.scalatest.matchers.should.Matchers

// class saTest extends AnyFlatSpec with ChiselScalatestTester {
//     "sa" should "pass" in {
//         test(new SystolicArray)
//             .withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
//             dut.io.in_a(0).poke(1.U)
//             dut.io.in_a(1).poke(0.U)
//             dut.io.in_a(2).poke(0.U)
//             dut.io.in_a(3).poke(0.U)
//             dut.io.in_b(0).poke(1.U)
//             dut.io.in_b(1).poke(0.U)
//             dut.io.in_b(2).poke(0.U)
//             dut.io.in_b(3).poke(0.U)

//             dut.clock.step()
            
//             dut.io.in_a(0).poke(2.U)
//             dut.io.in_a(1).poke(4.U)
//             dut.io.in_a(2).poke(0.U)
//             dut.io.in_a(3).poke(0.U)
//             dut.io.in_b(0).poke(4.U)
//             dut.io.in_b(1).poke(2.U)
//             dut.io.in_b(2).poke(0.U)
//             dut.io.in_b(3).poke(0.U)

//             dut.clock.step()

//             dut.io.in_a(0).poke(3.U)
//             dut.io.in_a(1).poke(3.U)
//             dut.io.in_a(2).poke(1.U)
//             dut.io.in_a(3).poke(0.U)
//             dut.io.in_b(0).poke(1.U)
//             dut.io.in_b(1).poke(3.U)
//             dut.io.in_b(2).poke(3.U)
//             dut.io.in_b(3).poke(0.U)

//             dut.clock.step()

//             dut.io.in_a(0).poke(4.U)
//             dut.io.in_a(1).poke(2.U)
//             dut.io.in_a(2).poke(2.U)
//             dut.io.in_a(3).poke(4.U)
//             dut.io.in_b(0).poke(4.U)
//             dut.io.in_b(1).poke(2.U)
//             dut.io.in_b(2).poke(2.U)
//             dut.io.in_b(3).poke(4.U)

//             dut.clock.step()

//             dut.io.in_a(0).poke(0.U)
//             dut.io.in_a(1).poke(1.U)
//             dut.io.in_a(2).poke(3.U)
//             dut.io.in_a(3).poke(3.U)
//             dut.io.in_b(0).poke(0.U)
//             dut.io.in_b(1).poke(3.U)
//             dut.io.in_b(2).poke(3.U)
//             dut.io.in_b(3).poke(1.U)

//             dut.clock.step()

//             dut.io.in_a(0).poke(0.U)
//             dut.io.in_a(1).poke(0.U)
//             dut.io.in_a(2).poke(4.U)
//             dut.io.in_a(3).poke(2.U)
//             dut.io.in_b(0).poke(0.U)
//             dut.io.in_b(1).poke(0.U)
//             dut.io.in_b(2).poke(2.U)
//             dut.io.in_b(3).poke(4.U)

//             dut.clock.step()

//             dut.io.in_a(0).poke(0.U)
//             dut.io.in_a(1).poke(0.U)
//             dut.io.in_a(2).poke(0.U)
//             dut.io.in_a(3).poke(1.U)
//             dut.io.in_b(0).poke(0.U)
//             dut.io.in_b(1).poke(0.U)
//             dut.io.in_b(2).poke(0.U)
//             dut.io.in_b(3).poke(1.U)

//             dut.clock.step()

//             dut.io.in_a(0).poke(0.U)
//             dut.io.in_a(1).poke(0.U)
//             dut.io.in_a(2).poke(0.U)
//             dut.io.in_a(3).poke(0.U)
//             dut.io.in_b(0).poke(0.U)
//             dut.io.in_b(1).poke(0.U)
//             dut.io.in_b(2).poke(0.U)
//             dut.io.in_b(3).poke(0.U)

//             for (i <- 0 until 9){
//                 dut.clock.step()
//             }
            

//             //println("mac_result is: " + dut.io.out_c.peek().toString)
//         }
//     }
// }