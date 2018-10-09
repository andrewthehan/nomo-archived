package com.github.andrewthehan.nomo.util.math

fun Vector<*, *>.toVector1f(): Vector1f {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return Vector1f(this[0].toFloat())
}
open class Vector1f(x: Float = 0f) : Vector1<Float, Vector1f>(x) {
  override fun toVectorType(init: (Int) -> Float) = Vector1f(init(0))
}

fun Vector<*, *>.toVector1i(): Vector1i {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return Vector1i(this[0].toInt())
}
open class Vector1i(x: Int = 0) : Vector1<Int, Vector1i>(x) {
  override fun toVectorType(init: (Int) -> Int) = Vector1i(init(0))
}

abstract class Vector1<NumberType : Number, VectorType: Vector<NumberType, *>>(x: NumberType = zero<NumberType>()) : AbstractVector<NumberType, VectorType>(x) {
  val x: NumberType
    get() = this[0]

  operator fun component1() = this[0]
}