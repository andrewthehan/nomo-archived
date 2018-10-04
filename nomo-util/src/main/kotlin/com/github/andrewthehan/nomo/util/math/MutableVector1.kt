package com.github.andrewthehan.nomo.util.math

open class MutableVector1f(x: Float = 0f) : MutableVector1<Float>(x)
open class MutableVector1i(x: Int = 0) : MutableVector1<Int>(x)

fun <NumberType : Number> Vector<NumberType>.toMutableVector1(): MutableVector1<NumberType> {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return MutableVector1(this[0])
}

operator fun <NumberType : Number> NumberType.times(vector: MutableVector1<NumberType>)
  = times(vector as Vector<NumberType>).toMutableVector1()

operator fun <NumberType : Number> NumberType.div(vector: MutableVector1<NumberType>)
  = div(vector as Vector<NumberType>).toMutableVector1()

open class MutableVector1<NumberType : Number>(x: NumberType = zero<NumberType>()) : MutableVector<NumberType>(x) {
  var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }

  operator fun component1() = this[0]

  operator fun plus(vector: Vector1<NumberType>) = super.plus(vector).toMutableVector1()
  
  operator fun minus(vector: Vector1<NumberType>) = super.minus(vector).toMutableVector1()

  override operator fun times(scalar: NumberType) = super.times(scalar).toMutableVector1()

  override operator fun div(scalar: NumberType) = super.div(scalar).toMutableVector1()

  override fun normalizeFloat() = super.normalizeFloat().toMutableVector1()

  override fun normalizeDouble() = super.normalizeDouble().toMutableVector1()
}