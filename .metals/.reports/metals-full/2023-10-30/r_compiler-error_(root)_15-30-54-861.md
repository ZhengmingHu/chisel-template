file://<WORKSPACE>/src/main/scala/SA/sa.scala
### scala.ScalaReflectionException: value t is not a method

occurred in the presentation compiler.

action parameters:
uri: file://<WORKSPACE>/src/main/scala/SA/sa.scala
text:
```scala
package PE

import chisel3._
import chisel3.util.Decoupled
import chisel3.util.Cat

class tickGenerator extends Module {
    val io = IO(new Bundle {
      val tick  = Output(Bool())
    })

    val tickCounterReg  = RegInit(0.U(8.W))
    val t  = tickCounterReg ===.U           // 秒表个位数每4个周期发一个tick
    io.tick  := t
    tickCounterReg := Mux(t, 0.U, tickCounterReg + 1.U)
}

class SystolicArray extends Module {
     val io = IO(new Bundle{
        val in_a      = Input(Vec(4, UInt(8.W)))
        val in_b      = Input(Vec(4, UInt(8.W)))

        val out_c     = Output(Vec(4, UInt(16.W)))
        val out_done  = Output(Bool())
    })

    def pipeReg(input: UInt): UInt = {
        val register = RegInit(0.U(16.W))
        register := input
        register
    }

    val macUnits       = Seq.fill(4)(Seq.fill(4)(Module(new macUnit)))
    //val regHorizontal  = RegInit(VecInit(Seq.fill(4)(VecInit(Seq.fill(3)(0.U(16.W))))))
    //val regVertical    = RegInit(VecInit(Seq.fill(3)(VecInit(Seq.fill(4)(0.U(16.W))))))
    val done           = WireInit(false.B)

    for (i <- 0 until 4) {
        for (j <- 0 until 4){
            if(j == 0){
                macUnits(i)(j).io.in_a := io.in_a(i)   
            }
            else if(i == 0){
                macUnits(i)(j).io.in_b := io.in_b(j)
                macUnits(i)(j).io.in_c := 0.U(16.W)   
            }
            else {
                macUnits(i)(j).io.in_a := pipeReg(macUnits(i)(j-1).io.out_a)
                macUnits(i)(j).io.in_b := pipeReg(macUnits(i-1)(j).io.out_b)
                macUnits(i)(j).io.in_c := pipeReg(macUnits(i-1)(j).io.out_c)
            }
        }
    }  
} 
```



#### Error stacktrace:

```
scala.reflect.api.Symbols$SymbolApi.asMethod(Symbols.scala:240)
	scala.reflect.api.Symbols$SymbolApi.asMethod$(Symbols.scala:234)
	scala.reflect.internal.Symbols$SymbolContextApiImpl.asMethod(Symbols.scala:99)
	scala.tools.nsc.typechecker.ContextErrors$TyperContextErrors$TyperErrorGen$.MissingArgsForMethodTpeError(ContextErrors.scala:761)
	scala.tools.nsc.typechecker.Typers$Typer.adaptMethodTypeToExpr$1(Typers.scala:972)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1276)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6056)
	scala.tools.nsc.typechecker.Typers$Typer.typedDefDef(Typers.scala:6318)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5950)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6041)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6119)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4(Typers.scala:3362)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4$adapted(Typers.scala:3357)
	scala.reflect.internal.Scopes$Scope.foreach(Scopes.scala:455)
	scala.tools.nsc.typechecker.Typers$Typer.addSynthetics$1(Typers.scala:3357)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3422)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2064)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1895)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5951)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6041)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6119)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3410)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3410)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5634)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5954)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6041)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:117)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:459)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1349)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1342)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:680)
	scala.meta.internal.pc.PcCollector.<init>(PcCollector.scala:29)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:18)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzycompute$1(PcSemanticTokensProvider.scala:18)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:18)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:71)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:157)
```
#### Short summary: 

scala.ScalaReflectionException: value t is not a method