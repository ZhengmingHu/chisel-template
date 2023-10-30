file://<WORKSPACE>/src/main/scala/SA/sa.scala
### java.lang.StringIndexOutOfBoundsException: offset 187, count -6, length 524

occurred in the presentation compiler.

action parameters:
offset: 181
uri: file://<WORKSPACE>/src/main/scala/SA/sa.scala
text:
```scala
package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

class SystolicArray extends Module {
     val io = IO(new Bundle{
        val in_a  = Input((Vec,@@UInt(8.W))
        val in_b  = Input(UInt(8.W))
        val out_c = Output(UInt(16.W))
    })
    val macUnits       = Seq.fill(4)(Seq.fill(4)(Module(new macUnit)))
    val regHorizontal  = RegInit(VecInit(Seq.fill(4)(VecInit(Seq.fill(3)(0.U(16.W))))))
    val regVertical    = RegInit(VecInit(Seq.fill(3)(VecInit(Seq.fill(4)(0.U(16.W))))))
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
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.traverse(SignatureHelpProvider.scala:275)
	scala.reflect.api.Trees$Traverser.traverseTrees(Trees.scala:2506)
	scala.reflect.internal.Trees$Apply.traverse(Trees.scala:789)
	scala.reflect.internal.Trees.itraverse(Trees.scala:1604)
	scala.reflect.internal.Trees.itraverse$(Trees.scala:1603)
	scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:28)
	scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:28)
	scala.reflect.api.Trees$Traverser.traverse(Trees.scala:2497)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.visit(SignatureHelpProvider.scala:350)
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

java.lang.StringIndexOutOfBoundsException: offset 187, count -6, length 524