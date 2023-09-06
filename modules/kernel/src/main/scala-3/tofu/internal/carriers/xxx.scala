package tofu.internal.carriers

import scala.concurrent.Future


object abc {
  class Kek[F[_]]

  def testPath[F[_]]: Kek[F] = new Kek[F] {
    override def toString(): String = "KEKE HAHA"
  }
}