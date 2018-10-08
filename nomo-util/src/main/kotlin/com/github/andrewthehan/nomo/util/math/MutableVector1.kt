package com.github.andrewthehan.nomo.util.math

/* Float */
operator fun Float.times(vector: MutableVector1f)
  = times(vector as Vector<Float>).toMutableVector1f()
operator fun Float.div(vector: MutableVector1f)
  = div(vector as Vector<Float>).toMutableVector1f()
fun <NumberType : Number> Vector<NumberType>.toMutableVector1f(): MutableVector1f {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1f(this[0].toFloat())
}
open class MutableVector1f(x: Float = 0f) : MutableVector1<Float>(x) {
  override operator fun plus(vector: Vector<Float>) = super.plus(vector).toMutableVector1f()
  override operator fun minus(vector: Vector<Float>) = super.minus(vector).toMutableVector1f()
  override operator fun times(scalar: Float) = super.times(scalar).toMutableVector1f()
  override operator fun div(scalar: Float) = super.div(scalar).toMutableVector1f()
}

/* Int */
operator fun Int.times(vector: MutableVector1i)
  = times(vector as Vector<Int>).toMutableVector1i()
operator fun Int.div(vector: MutableVector1i)
  = div(vector as Vector<Int>).toMutableVector1i()
fun <NumberType : Number> Vector<NumberType>.toMutableVector1i(): MutableVector1i {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1i(this[0].toInt())
}
open class MutableVector1i(x: Int = 0) : MutableVector1<Int>(x) {
  override operator fun plus(vector: Vector<Int>) = super.plus(vector).toMutableVector1i()
  override operator fun minus(vector: Vector<Int>) = super.minus(vector).toMutableVector1i()
  override operator fun times(scalar: Int) = super.times(scalar).toMutableVector1i()
  override operator fun div(scalar: Int) = super.div(scalar).toMutableVector1i()
}

/* Base */
operator fun <NumberType : Number> NumberType.times(vector: MutableVector1<NumberType>)
  = times(vector as Vector<NumberType>).toMutableVector1()
operator fun <NumberType : Number> NumberType.div(vector: MutableVector1<NumberType>)
  = div(vector as Vector<NumberType>).toMutableVector1()
fun <NumberType : Number> Vector<NumberType>.toMutableVector1(): MutableVector1<NumberType> {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1(this[0])
}
open class MutableVector1<NumberType : Number>(x: NumberType = zero<NumberType>()) : MutableVector<NumberType>(x) {
  var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }

  operator fun component1() = this[0]

  override operator fun plus(vector: Vector<NumberType>) = super.plus(vector).toMutableVector1()
  override operator fun minus(vector: Vector<NumberType>) = super.minus(vector).toMutableVector1()
  override operator fun times(scalar: NumberType) = super.times(scalar).toMutableVector1()
  override operator fun div(scalar: NumberType) = super.div(scalar).toMutableVector1()
  override fun normalizeFloat() = super.normalizeFloat().toMutableVector1()
  override fun normalizeDouble() = super.normalizeDouble().toMutableVector1()
}