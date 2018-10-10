package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

fun Vector<*, *>.toVector3f(): Vector3f {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return Vector3f(this[0].toFloat(), this[1].toFloat(), this[2].toFloat())
}
open class Vector3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3<Float, Vector3f>(x, y, z) {
  override fun vectorTypeOf(init: (Int) -> Float) = Vector3f(init(0), init(1), init(2))
}

fun Vector<*, *>.toVector3i(): Vector3i {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return Vector3i(this[0].toInt(), this[1].toInt(), this[2].toInt())
}
open class Vector3i(x: Int = 0, y: Int = 0, z: Int = 0) : Vector3<Int, Vector3i>(x, y, z) {
  override fun vectorTypeOf(init: (Int) -> Int) = Vector3i(init(0), init(1), init(2))
}

abstract class Vector3<NumberType : Number, VectorType: Vector<NumberType, VectorType>>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>(), z: NumberType = zero<NumberType>()) : AbstractVector<NumberType, VectorType>(x, y, z) {
  val x: NumberType
    get() = this[0]
  val y: NumberType
    get() = this[1]
  val z: NumberType
    get() = this[2]

  operator fun component1() = this[0]
  operator fun component2() = this[1]
  operator fun component3() = this[2]
}