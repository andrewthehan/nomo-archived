package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

fun Vector<*, *>.toMutableVector3f(): MutableVector3f {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return MutableVector3f(this[0].toFloat(), this[1].toFloat(), this[2].toFloat())
}
open class MutableVector3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3<Float, MutableVector3f>(x, y, z) {
  override fun vectorTypeOf(init: (Int) -> Float) = MutableVector3f(init(0), init(1), init(2))
}

fun Vector<*, *>.toMutableVector3i(): MutableVector3i {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return MutableVector3i(this[0].toInt(), this[1].toInt(), this[2].toInt())
}
open class MutableVector3i(x: Int = 0, y: Int = 0, z: Int = 0) : MutableVector3<Int, MutableVector3i>(x, y, z) {
  override fun vectorTypeOf(init: (Int) -> Int) = MutableVector3i(init(0), init(1), init(2))
}

abstract class MutableVector3<NumberType : Number, VectorType: MutableVector<NumberType, VectorType>>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>(), z: NumberType = zero<NumberType>()) : AbstractMutableVector<NumberType, VectorType>(x, y, z) {
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
  var z: NumberType
    get() = this[2]
    set(value: NumberType) {
      this[2] = value
    }

  operator fun component1() = this[0]
  operator fun component2() = this[1]
  operator fun component3() = this[2]
}