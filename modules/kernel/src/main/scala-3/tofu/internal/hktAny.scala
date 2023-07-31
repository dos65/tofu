package tofu.internal

object hktAny {
  type AnyK = AnyKind
  type AnyKK[+A, +B] = Any
}
