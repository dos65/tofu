package tofu.internal.carriers

import scala.concurrent.Future

object xxx {

  import tofu.internal.carriers.abc.Kek

  implicit def instance[F[_]]: Kek[F] = {
    TestMacro.delegate0[F]("tofu.internal.carriers.abc.testPath")
  }
}