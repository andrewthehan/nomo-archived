package com.github.andrewthehan.nomo.util.math

/* Float */
operator fun Float.times(vector: Vector1f)
  = times(vector as Vector<Float>).toVector1f()
operator fun Float.div(vector: Vector1f)
  = div(vector as Vector<Float>).toVector1f()
fun Vector<*>.toVector1f(): Vector1f {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return Vector1f(this[0].toFloat())
}
open class Vector1f(x: Float = 0f) : Vector1<Float>(x) {
  override operator fun plus(vector: Vector<Float>) = super.plus(vector).toVector1f()
  override operator fun minus(vector: Vector<Float>) = super.minus(vector).toVector1f()
  override operator fun times(scalar: Float) = super.times(scalar).toVector1f()
  override operator fun div(scalar: Float) = super.div(scalar).toVector1f()
}

/* Int */
operator fun Int.times(vector: Vector1i)
  = times(vector as Vector<Int>).toVector1i()
operator fun Int.div(vector: Vector1i)
  = div(vector as Vector<Int>).toVector1i()
fun Vector<*>.toVector1i(): Vector1i {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return Vector1i(this[0].toInt())
}
open class Vector1i(x: Int = 0) : Vector1<Int>(x) {
  override operator fun plus(vector: Vector<Int>) = super.plus(vector).toVector1i()
  override operator fun minus(vector: Vector<Int>) = super.minus(vector).toVector1i()
  override operator fun times(scalar: Int) = super.times(scalar).toVector1i()
  override operator fun div(scalar: Int) = super.div(scalar).toVector1i()
}

/* Base */
operator fun <NumberType : Number> NumberType.times(vector: Vector1<NumberType>)
  = times(vector as Vector<NumberType>).toVector1()
operator fun <NumberType : Number> NumberType.div(vector: Vector1<NumberType>)
  = div(vector as Vector<NumberType>).toVector1()
fun <NumberType : Number> Vector<NumberType>.toVector1(): Vector1<NumberType> {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return Vector1(this[0])
}
open class Vector1<NumberType : Number>(x: NumberType = zero<NumberType>()) : Vector<NumberType>(x) {
  val x: NumberType
    get() = this[0]

  operator fun component1() = this[0]

  override operator fun plus(vector: Vector<NumberType>) = super.plus(vector).toVector1()
  override operator fun minus(vector: Vector<NumberType>) = super.minus(vector).toVector1()
  override operator fun times(scalar: NumberType) = super.times(scalar).toVector1()
  override operator fun div(scalar: NumberType) = super.div(scalar).toVector1()
  override fun normalizeFloat() = super.normalizeFloat().toVector1()
  override fun normalizeDouble() = super.normalizeDouble().toVector1()
}