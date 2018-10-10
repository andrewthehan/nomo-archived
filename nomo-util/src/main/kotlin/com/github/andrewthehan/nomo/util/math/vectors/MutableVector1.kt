package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

fun Vector<*, *>.toMutableVector1f(): MutableVector1f {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1f(this[0].toFloat())
}
open class MutableVector1f(x: Float = 0f) : MutableVector1<Float, MutableVector1f>(x) {
  override fun vectorTypeOf(init: (Int) -> Float) = MutableVector1f(init(0))
}

fun Vector<*, *>.toMutableVector1i(): MutableVector1i {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1i(this[0].toInt())
}
open class MutableVector1i(x: Int = 0) : MutableVector1<Int, MutableVector1i>(x) {
  override fun vectorTypeOf(init: (Int) -> Int) = MutableVector1i(init(0))
}

abstract class MutableVector1<NumberType : Number, VectorType: MutableVector<NumberType, VectorType>>(x: NumberType = zero<NumberType>()) : AbstractMutableVector<NumberType, VectorType>(x) {
  var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }

  operator fun component1() = this[0]
}