package com.github.andrewthehan.nomo.util.math

open class Vector1f(x: Float = 0f) : Vector1<Float>(x)
open class Vector1i(x: Int = 0) : Vector1<Int>(x)

fun <NumberType : Number> Vector<NumberType>.toVector1(): Vector1<NumberType> {
  require(this.dimensions == 1) { "Vector dimensions should be 1: ${this.dimensions}" }
  return Vector1(this[0])
}

operator fun <NumberType : Number> NumberType.times(vector: Vector1<NumberType>)
  = times(vector as Vector<NumberType>).toVector1()

operator fun <NumberType : Number> NumberType.div(vector: Vector1<NumberType>)
  = div(vector as Vector<NumberType>).toVector1()

open class Vector1<NumberType : Number>(x: NumberType = zero<NumberType>()) : Vector<NumberType>(x) {
  val x: NumberType
    get() = this[0]

  operator fun component1() = this[0]

  operator fun plus(vector: Vector1<NumberType>) = super.plus(vector).toVector1()
  
  operator fun minus(vector: Vector1<NumberType>) = super.minus(vector).toVector1()

  override operator fun times(scalar: NumberType) = super.times(scalar).toVector1()

  override operator fun div(scalar: NumberType) = super.div(scalar).toVector1()

  override fun normalizeFloat() = super.normalizeFloat().toVector1()

  override fun normalizeDouble() = super.normalizeDouble().toVector1()
}