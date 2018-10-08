package com.github.andrewthehan.nomo.util.math

/* Float */
operator fun Float.times(vector: MutableVector2f)
  = times(vector as Vector<Float>).toMutableVector2f()
operator fun Float.div(vector: MutableVector2f)
  = div(vector as Vector<Float>).toMutableVector2f()
fun Vector<*>.toMutableVector2f(): MutableVector2f {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return MutableVector2f(this[0].toFloat(), this[1].toFloat())
}
open class MutableVector2f(x: Float = 0f, y: Float = 0f) : MutableVector2<Float>(x, y) {
  override operator fun plus(vector: Vector<Float>) = super.plus(vector).toMutableVector2f()
  override operator fun minus(vector: Vector<Float>) = super.minus(vector).toMutableVector2f()
  override operator fun times(scalar: Float) = super.times(scalar).toMutableVector2f()
  override operator fun div(scalar: Float) = super.div(scalar).toMutableVector2f()
}

/* Int */
operator fun Int.times(vector: MutableVector2i)
  = times(vector as Vector<Int>).toMutableVector2i()
operator fun Int.div(vector: MutableVector2i)
  = div(vector as Vector<Int>).toMutableVector2i()
fun Vector<*>.toMutableVector2i(): MutableVector2i {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return MutableVector2i(this[0].toInt(), this[1].toInt())
}
open class MutableVector2i(x: Int = 0, y: Int = 0) : MutableVector2<Int>(x, y) {
  override operator fun plus(vector: Vector<Int>) = super.plus(vector).toMutableVector2i()
  override operator fun minus(vector: Vector<Int>) = super.minus(vector).toMutableVector2i()
  override operator fun times(scalar: Int) = super.times(scalar).toMutableVector2i()
  override operator fun div(scalar: Int) = super.div(scalar).toMutableVector2i()
}

/* Base */
operator fun <NumberType : Number> NumberType.times(vector: MutableVector2<NumberType>)
  = times(vector as Vector<NumberType>).toMutableVector2()
operator fun <NumberType : Number> NumberType.div(vector: MutableVector2<NumberType>)
  = div(vector as Vector<NumberType>).toMutableVector2()
fun <NumberType : Number> Vector<NumberType>.toMutableVector2(): MutableVector2<NumberType> {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return MutableVector2(this[0], this[1])
}
open class MutableVector2<NumberType : Number>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>()) : MutableVector<NumberType>(x, y) {
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

  override operator fun plus(vector: Vector<NumberType>) = super.plus(vector).toMutableVector2()
  override operator fun minus(vector: Vector<NumberType>) = super.minus(vector).toMutableVector2()
  override operator fun times(scalar: NumberType) = super.times(scalar).toMutableVector2()
  override operator fun div(scalar: NumberType) = super.div(scalar).toMutableVector2()
  override fun normalizeFloat() = super.normalizeFloat().toMutableVector2()
  override fun normalizeDouble() = super.normalizeDouble().toMutableVector2()
}