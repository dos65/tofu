package tofu.internal.carriers

import scala.concurrent.Future

object xxx {
  TestMacro.delegate0[Future]("tofu.internal.carriers.abc.testPath")
}

object abc {
  class Kek[F[_]]

  def testPath[F[_]]: Kek[F] = new Kek[F] {
    override def toString(): String = "KEKE HAHA"
  }
}