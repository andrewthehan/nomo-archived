package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

fun Vector<*, *>.toMutableVector2f(): MutableVector2f {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return MutableVector2f(this[0].toFloat(), this[1].toFloat())
}
open class MutableVector2f(x: Float = 0f, y: Float = 0f) : MutableVector2<Float, MutableVector2f>(x, y) {
  override fun vectorTypeOf(init: (Int) -> Float) = MutableVector2f(init(0), init(1))
}

fun Vector<*, *>.toMutableVector2i(): MutableVector2i {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return MutableVector2i(this[0].toInt(), this[1].toInt())
}
open class MutableVector2i(x: Int = 0, y: Int = 0) : MutableVector2<Int, MutableVector2i>(x, y) {
  override fun vectorTypeOf(init: (Int) -> Int) = MutableVector2i(init(0), init(1))
}

abstract class MutableVector2<NumberType : Number, VectorType: MutableVector<NumberType, VectorType>>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>()) : AbstractMutableVector<NumberType, VectorType>(x, y) {
  var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }
  var y: NumberType
    get() = this[1]
    set(value: NumberType) {
      this[1] = value
    }

  operator fun component1() = this[0]
  operator fun component2() = this[1]
}