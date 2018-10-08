package com.github.andrewthehan.nomo.util.math

/* Float */
operator fun Float.times(vector: Vector2f)
  = times(vector as Vector<Float>).toVector2f()
operator fun Float.div(vector: Vector2f)
  = div(vector as Vector<Float>).toVector2f()
fun Vector<*>.toVector2f(): Vector2f {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return Vector2f(this[0].toFloat(), this[1].toFloat())
}
open class Vector2f(x: Float = 0f, y: Float = 0f) : Vector2<Float>(x, y) {
  override operator fun plus(vector: Vector<Float>) = super.plus(vector).toVector2f()
  override operator fun minus(vector: Vector<Float>) = super.minus(vector).toVector2f()
  override operator fun times(scalar: Float) = super.times(scalar).toVector2f()
  override operator fun div(scalar: Float) = super.div(scalar).toVector2f()
}

/* Int */
operator fun Int.times(vector: Vector2i)
  = times(vector as Vector<Int>).toVector2i()
operator fun Int.div(vector: Vector2i)
  = div(vector as Vector<Int>).toVector2i()
fun Vector<*>.toVector2i(): Vector2i {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return Vector2i(this[0].toInt(), this[1].toInt())
}
open class Vector2i(x: Int = 0, y: Int = 0) : Vector2<Int>(x, y) {
  override operator fun plus(vector: Vector<Int>) = super.plus(vector).toVector2i()
  override operator fun minus(vector: Vector<Int>) = super.minus(vector).toVector2i()
  override operator fun times(scalar: Int) = super.times(scalar).toVector2i()
  override operator fun div(scalar: Int) = super.div(scalar).toVector2i()
}

/* Base */
operator fun <NumberType : Number> NumberType.times(vector: Vector2<NumberType>)
  = times(vector as Vector<NumberType>).toVector2()
operator fun <NumberType : Number> NumberType.div(vector: Vector2<NumberType>)
  = div(vector as Vector<NumberType>).toVector2()
fun <NumberType : Number> Vector<NumberType>.toVector2(): Vector2<NumberType> {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return Vector2(this[0], this[1])
}
open class Vector2<NumberType : Number>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>()) : Vector<NumberType>(x, y) {
  val x: NumberType
    get() = this[0]
  val y: NumberType
    get() = this[1]

  operator fun component1() = this[0]
  operator fun component2() = this[1]

  override operator fun plus(vector: Vector<NumberType>) = super.plus(vector).toVector2()
  override operator fun minus(vector: Vector<NumberType>) = super.minus(vector).toVector2()
  override operator fun times(scalar: NumberType) = super.times(scalar).toVector2()
  override operator fun div(scalar: NumberType) = super.div(scalar).toVector2()
  override fun normalizeFloat() = super.normalizeFloat().toVector2()
  override fun normalizeDouble() = super.normalizeDouble().toVector2()
}