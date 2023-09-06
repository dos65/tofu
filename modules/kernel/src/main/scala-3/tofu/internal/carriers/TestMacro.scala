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

    //val start: Tree = Ident(root.termRef).
    val wtf = tail.foldLeft(root.termRef){(acc, s) => acc.select(s).termSymbol.termRef}
    val sample = '{ object A {def foo: Int = 42} ;  A.foo}
    println("++++++++++++++")
    println(sample.asTerm)
    println("++++++++++++++")
    println("~~~~~~~~~~~~~~~~~~~~")
    println(wtf.termSymbol.tree)
    println("~~~~~~~~~~~~~~~~~~~~")
    //'{ wtf}
    println(wtf)
    //Select()
    //Select(root, )
    
    //val out = Apply(TypeApply(wtf.asType, List(TypeTree.of[F])), List.empty)
    ???
    // out.asExpr
}
