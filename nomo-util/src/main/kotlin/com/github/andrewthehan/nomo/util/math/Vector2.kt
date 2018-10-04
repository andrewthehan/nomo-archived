package com.github.andrewthehan.nomo.util.math

open class Vector2f(x: Float = 0f, y: Float = 0f) : Vector2<Float>(x, y)
open class Vector2i(x: Int = 0, y: Int = 0) : Vector2<Int>(x, y)

fun <NumberType : Number> Vector<NumberType>.toVector2(): Vector2<NumberType> {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return Vector2(this[0], this[1])
}

operator fun <NumberType : Number> NumberType.times(vector: Vector2<NumberType>)
  = times(vector as Vector<NumberType>).toVector2()

operator fun <NumberType : Number> NumberType.div(vector: Vector2<NumberType>)
  = div(vector as Vector<NumberType>).toVector2()

open class Vector2<NumberType : Number>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>()) : Vector<NumberType>(x, y) {
  val x: NumberType
    get() = this[0]
      
  val y: NumberType
    get() = this[1]

  operator fun component1() = this[0]
  
  operator fun component2() = this[1]

  operator fun plus(vector: Vector2<NumberType>) = super.plus(vector).toVector2()
  
  operator fun minus(vector: Vector2<NumberType>) = super.minus(vector).toVector2()

  override operator fun times(scalar: NumberType) = super.times(scalar).toVector2()

  override operator fun div(scalar: NumberType) = super.div(scalar).toVector2()

  override fun normalizeFloat() = super.normalizeFloat().toVector2()

  override fun normalizeDouble() = super.normalizeDouble().toVector2()
}