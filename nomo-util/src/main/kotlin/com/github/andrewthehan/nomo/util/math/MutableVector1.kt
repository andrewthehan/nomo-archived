package com.github.andrewthehan.nomo.util.math

fun Vector<*, *>.toMutableVector1f(): MutableVector1f {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1f(this[0].toFloat())
}
open class MutableVector1f(x: Float = 0f) : MutableVector1<Float, MutableVector1f>(x) {
  override fun toVectorType(init: (Int) -> Float) = MutableVector1f(init(0))
}

fun Vector<*, *>.toMutableVector1i(): MutableVector1i {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1i(this[0].toInt())
}
open class MutableVector1i(x: Int = 0) : MutableVector1<Int, MutableVector1i>(x) {
  override fun toVectorType(init: (Int) -> Int) = MutableVector1i(init(0))
}

abstract class MutableVector1<NumberType : Number, VectorType: MutableVector<NumberType, *>>(x: NumberType = zero<NumberType>()) : AbstractMutableVector<NumberType, VectorType>(x), MutableVector<NumberType, VectorType> {
  var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }

  operator fun component1() = this[0]
}