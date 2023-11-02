// package tofu

// import scala.annotation.unchecked.uncheckedVariance
// import scala.concurrent.duration._

// import cats.Monad
// import cats.data.ReaderT
// import cats.effect.std.Dispatcher
// import cats.effect.{IO, Resource}
// import tofu.generate.GenRandom
// import tofu.syntax.monadic._
// import tofu.syntax.scoped._
// import tofu.syntax.time._
// import tofu.time.Sleep
// import tofu.{Execute, PerformThrow, WithContext}
// import tofu.concurrent.Atom
// import tofu.concurrent.MakeAtom
// import scala.concurrent.Future

// case class MyContext(dispatcher: Dispatcher[IO], traceId: Long)
// object MyContext {
//   type Reader[+A] = ReaderT[IO, MyContext, A @uncheckedVariance]

//   implicit val withDispatcher: WithContext[Reader, Dispatcher[IO]] =
//     WithContext[Reader[_], MyContext].extract(_.dispatcher)
// }

// class PerformSuite extends scala.App {
//   type Eff[+A] = MyContext.Reader[A]

//   val aaaa = implicitly[tofu.interop.ContextDispatch[Eff[_]]]
//   //val bbb = implicitly[tofu.interop.ContextDispatch[IO[_]]]

//   val p = PerformThrow[Eff[_]]

//   val genTraceId = for {
//     random  <- GenRandom.instance[IO, IO]()
//     traceId <- random.nextLong
//   } yield traceId

//   val init = for {
//     dispatcher <- Dispatcher.parallel[IO]
//     traceId    <- Resource.eval(genTraceId)
//   } yield MyContext(dispatcher, traceId)

//   def waitUpdate[F[_]: Sleep: Monad](atom: Atom[F, Int]): F[Int] = for {
//     v <- atom.get
//     _ <- sleep(100.millis)
//     _ <- atom.update(_ + 1)
//   } yield v

//   def program[F[_]: PerformThrow: Monad: Execute, A](fa: F[A]) = for {
//     performer <- PerformThrow[F].performer
//     (fut1, _)  = performer.toFuture(fa)
//     (fut2, _)  = performer.toFuture(fa)
//     (fut3, _)  = performer.toFuture(fa)
//     res1      <- deferFuture(_ => fut1)
//     res2      <- deferFuture(_ => fut2)
//     res3      <- deferFuture(_ => fut3)
//   } yield List(res1, res2, res3)

//   val run = for {
//     atom <- MakeAtom[Eff[_], Eff[_]].of(1)
//     ress <- program(waitUpdate(atom))
//   } yield ress
// }
