package tofu.logging

import tofu.higherKind.RepresentableK
import tofu.higherKind

import scala.annotation.nowarn

trait LoggingRepresentableKInstances {
  @nowarn("cat=w-flag-self-implicit")
  implicit val loggingRepresentable: RepresentableK[Logging] = higherKind.derived.genRepresentableK[Logging]
}
