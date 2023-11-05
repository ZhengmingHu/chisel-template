file://<WORKSPACE>/src/main/scala/SystolicArray/SA/SA.scala
### java.lang.StringIndexOutOfBoundsException: offset 805, count -8, length 1825

occurred in the presentation compiler.

action parameters:
offset: 797
uri: file://<WORKSPACE>/src/main/scala/SystolicArray/SA/SA.scala
text:
```scala
package SA

import chisel3._


// C = A * B
// A: INT8  B: INT8  C: INT32
class SA(val IN_WIDTH: Int, val C_WIDTH: Int, val SA_ROWS: Int, val SA_COLS: Int) extends Module {
  val io = IO(new Bundle {
    val in_control = Input(Vec(SA_COLS, new PEControl))
    val in_a = Input(Vec(SA_ROWS, UInt(IN_WIDTH.W)))
    val in_b = Input(Vec(SA_COLS, UInt(IN_WIDTH.W)))
    val in_c = Input(Vec(SA_COLS, UInt(C_WIDTH.W)))

    val out_control = Output(Vec(SA_COLS, new PEControl))
    val out_a = Output(Vec(SA_ROWS, UInt(IN_WIDTH.W)))
    val out_b = Output(Vec(SA_COLS, UInt(IN_WIDTH.W)))
    val out_c = Output(Vec(SA_COLS, UInt(C_WIDTH.W)))
  })

  val sa = Seq.fill(SA_ROWS, SA_COLS)(Module(new PE(IN_WIDTH, C_WIDTH)))
  val sa_t = sa.transpose

  val controller = Vec(SA_COLS,@@Module(new Controller(SA_ROWS, SA_COLS))


  for (r <- 0 until SA_ROWS) {
    sa(r).foldLeft(io.in_a(r)) {
      case (in_a, pe) =>     // in_a的初始值为io.in_a(r)
        pe.io.in_a := in_a   // 对每个pe，从左向右依次操作
        pe.io.out_a
    }
  }

  for (c <- 0 until SA_COLS) {
    sa_t(c).foldLeft(io.in_b(c)) {
      case (in_b, pe) =>
        pe.io.in_b := in_b
        pe.io.out_b
    }
  }

  for (c <- 0 until SA_COLS) {
    sa_t(c).foldLeft(io.in_c(c)) {
      case (in_c, pe) =>
        pe.io.in_c := in_c
        pe.io.out_c
    }
  }


  for (c <- 0 until SA_COLS) {
    sa_t(c).foldLeft(io.in_control(c)) {
      case (in_ctrl, pe) =>
        pe.io.in_control := in_ctrl
        pe.io.out_control
    }
  }

  for (c <- 0 until SA_COLS) {
    io.out_b(c) := sa(SA_ROWS - 1)(c).io.out_b
    io.out_c(c) := sa(SA_ROWS - 1)(c).io.out_c
    io.out_control(c) := sa(SA_ROWS-1)(c).io.out_control
  }

  for (r <- 0 until SA_ROWS) {
    io.out_a(r) := sa(r)(SA_COLS-1).io.out_a
  }

}


```



#### Error stacktrace:

```
java.base/java.lang.String.checkBoundsOffCount(String.java:3304)
	java.base/java.lang.String.rangeCheck(String.java:280)
	java.base/java.lang.String.<init>(String.java:276)
	scala.tools.nsc.interactive.Global.typeCompletions$1(Global.scala:1224)
	scala.tools.nsc.interactive.Global.completionsAt(Global.scala:1262)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$treeSymbol$1(SignatureHelpProvider.scala:390)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.pc.SignatureHelpProvider.treeSymbol(SignatureHelpProvider.scala:388)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCall$.unapply(SignatureHelpProvider.scala:205)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.visit(SignatureHelpProvider.scala:316)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.traverse(SignatureHelpProvider.scala:310)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.$anonfun$visit$5(SignatureHelpProvider.scala:346)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.$anonfun$visit$5$adapted(SignatureHelpProvider.scala:323)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:563)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:561)
	scala.collection.AbstractIterable.foreach(Iterable.scala:926)
	scala.collection.IterableOps$WithFilter.foreach(Iterable.scala:896)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.$anonfun$visit$3(SignatureHelpProvider.scala:323)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.$anonfun$visit$3$adapted(SignatureHelpProvider.scala:322)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:563)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:561)
	scala.collection.AbstractIterable.foreach(Iterable.scala:926)
	scala.collection.IterableOps$WithFilter.foreach(Iterable.scala:896)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.visit(SignatureHelpProvider.scala:322)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.traverse(SignatureHelpProvider.scala:310)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.fromTree(SignatureHelpProvider.scala:279)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:27)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:282)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: offset 805, count -8, length 1825