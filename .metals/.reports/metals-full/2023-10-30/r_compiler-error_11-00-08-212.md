file://<WORKSPACE>/mac.scala
### file%3A%2F%2F%2Fhome%2Fzcy%2FchiselProj%2FMyChisel%2Fmac.scala:6: error: illegal start of definition identifier
<WORKSPACE>/mac.scala
^

occurred in the presentation compiler.

action parameters:
uri: file://<WORKSPACE>/mac.scala
text:
```scala
package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat
<WORKSPACE>/mac.scala
class macUnit extends Module {
    val io = IO(new Bundle{
        val done  = Input(Bool())
        val in_a  = Input(UInt(8.W))
        val in_b  = Input(UInt(8.W))
        val in_c  = Input(UInt(16.W))
        val out_c = Outuput(UInt(16.W))
    })
    val mul   = Module(new Multiplier())
    val rca   = Module(new RCA()) 

    val c_reg = RegInit(0.U(16.W))

    mul.MultiplierInputBundle.multiplicand := io.in_a
    mul.MultiplierInputBundle.multiplier   := io.in_b
    rca.input.a_in                         := mul.MultiplierOutputBundle.product
    rca.input.b_in                         := c_reg
    rca.input.c_in                         := 0.U(16.W)
    
    c_reg := Mux(io.done === true.B, io.in_c, rca.output.S)
}
```



#### Error stacktrace:

```
scala.meta.internal.parsers.Reporter.syntaxError(Reporter.scala:16)
	scala.meta.internal.parsers.Reporter.syntaxError$(Reporter.scala:16)
	scala.meta.internal.parsers.Reporter$$anon$1.syntaxError(Reporter.scala:22)
	scala.meta.internal.parsers.Reporter.syntaxError(Reporter.scala:17)
	scala.meta.internal.parsers.Reporter.syntaxError$(Reporter.scala:17)
	scala.meta.internal.parsers.Reporter$$anon$1.syntaxError(Reporter.scala:22)
	scala.meta.internal.parsers.ScalametaParser.statSeqBuf(ScalametaParser.scala:4386)
	scala.meta.internal.parsers.ScalametaParser.bracelessPackageStats$1(ScalametaParser.scala:4603)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$batchSource$11(ScalametaParser.scala:4614)
	scala.meta.internal.parsers.ScalametaParser.autoEndPos(ScalametaParser.scala:361)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$batchSource$10(ScalametaParser.scala:4614)
	scala.meta.internal.parsers.ScalametaParser.tryParse(ScalametaParser.scala:209)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$batchSource$1(ScalametaParser.scala:4606)
	scala.meta.internal.parsers.ScalametaParser.atPos(ScalametaParser.scala:312)
	scala.meta.internal.parsers.ScalametaParser.autoPos(ScalametaParser.scala:358)
	scala.meta.internal.parsers.ScalametaParser.batchSource(ScalametaParser.scala:4574)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$source$1(ScalametaParser.scala:4567)
	scala.meta.internal.parsers.ScalametaParser.atPos(ScalametaParser.scala:312)
	scala.meta.internal.parsers.ScalametaParser.autoPos(ScalametaParser.scala:358)
	scala.meta.internal.parsers.ScalametaParser.source(ScalametaParser.scala:4567)
	scala.meta.internal.parsers.ScalametaParser.entrypointSource(ScalametaParser.scala:4572)
	scala.meta.internal.parsers.ScalametaParser.parseSourceImpl(ScalametaParser.scala:135)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$parseSource$1(ScalametaParser.scala:132)
	scala.meta.internal.parsers.ScalametaParser.parseRuleAfterBOF(ScalametaParser.scala:59)
	scala.meta.internal.parsers.ScalametaParser.parseRule(ScalametaParser.scala:54)
	scala.meta.internal.parsers.ScalametaParser.parseSource(ScalametaParser.scala:132)
	scala.meta.parsers.Parse$.$anonfun$parseSource$1(Parse.scala:29)
	scala.meta.parsers.Parse$$anon$1.apply(Parse.scala:36)
	scala.meta.parsers.Api$XtensionParseDialectInput.parse(Api.scala:25)
	scala.meta.internal.semanticdb.scalac.ParseOps$XtensionCompilationUnitSource.toSource(ParseOps.scala:17)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument.toTextDocument(TextDocumentOps.scala:206)
	scala.meta.internal.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:54)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticdbTextDocument$1(ScalaPresentationCompiler.scala:356)
```
#### Short summary: 

file%3A%2F%2F%2Fhome%2Fzcy%2FchiselProj%2FMyChisel%2Fmac.scala:6: error: illegal start of definition identifier
<WORKSPACE>/mac.scala
^