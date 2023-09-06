package tofu.internal.carriers

import scala.quoted.*

object TestMacro {

  transparent inline def delegate0[F[_]](inline path: String) = 
    ${ mkDelegate[F]('path)}

  def mkDelegate[F[_]: Type](path: Expr[String])(using Quotes): Expr[Any] = 
    import quotes.reflect._
    val sym = Symbol.requiredMethod(path.valueOrAbort)

  //  println(sym.isTerm)
    // val expr = sym.tree.asExpr
    // val term = expr.asTerm

    var iterSym = sym
    val acc = List.newBuilder[Symbol]

    iterSym.isNoSymbol
    while !iterSym.isNoSymbol do
      println(iterSym)
      acc += iterSym
      iterSym = iterSym.owner

    val root :: tail = acc.result().reverse
    val start: Term = Ident(root.termRef)
    val wtf = tail.foldLeft(start){(acc, s) => acc.select(s)}

    // val sample = 
    //   '{
    //     object foo {
    //       class X[F[_]]
    //       def testPath[F[_]]: X[F] = ???
    //     }
    //     object bar {
    //       foo.testPath[scala.concurrent.Future]
    //     }
    //   }

    // println(sample.asTerm)
    

    // List(
    //  TypeApply(Select(Ident(foo),testPath),List(Select(Select(Ident(scala),concurrent),Future))))))),
    //  Literal(Constant(()))
    // )
    val out = TypeApply(wtf, List(TypeTree.of[F]))
    val expr = out.asExpr
    println(out.show)



    expr
}
