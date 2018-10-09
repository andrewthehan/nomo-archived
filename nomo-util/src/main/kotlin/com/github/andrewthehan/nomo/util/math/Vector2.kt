package com.github.andrewthehan.nomo.util.math

fun Vector<*, *>.toVector2f(): Vector2f {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return Vector2f(this[0].toFloat(), this[1].toFloat())
}
open class Vector2f(x: Float = 0f, y: Float = 0f) : Vector2<Float, Vector2f>(x, y) {
  override fun toVectorType(init: (Int) -> Float) = Vector2f(init(0), init(1))
}

fun Vector<*, *>.toVector2i(): Vector2i {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return Vector2i(this[0].toInt(), this[1].toInt())
}
open class Vector2i(x: Int = 0, y: Int = 0) : Vector2<Int, Vector2i>(x, y) {
  override fun toVectorType(init: (Int) -> Int) = Vector2i(init(0), init(1))
}

abstract class Vector2<NumberType : Number, VectorType: Vector<NumberType, *>>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>()) : AbstractVector<NumberType, VectorType>(x, y) {
  val x: NumberType
    get() = this[0]
  val y: NumberType
    get() = this[1]

  operator fun component1() = this[0]
  operator fun component2() = this[1]
}