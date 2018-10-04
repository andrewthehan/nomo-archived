package com.github.andrewthehan.nomo.util.math

open class MutableVector2f(x: Float = 0f, y: Float = 0f) : MutableVector2<Float>(x, y)
open class MutableVector2i(x: Int = 0, y: Int = 0) : MutableVector2<Int>(x, y)

fun <NumberType : Number> Vector<NumberType>.toMutableVector2(): MutableVector2<NumberType> {
  require(this.dimensions == 2) { "Vector dimensions should be 2: ${this.dimensions}" }
  return MutableVector2(this[0], this[1])
}

operator fun <NumberType : Number> NumberType.times(vector: MutableVector2<NumberType>)
  = times(vector as Vector<NumberType>).toMutableVector2()

operator fun <NumberType : Number> NumberType.div(vector: MutableVector2<NumberType>)
  = div(vector as Vector<NumberType>).toMutableVector2()

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

  operator fun plus(vector: Vector2<NumberType>) = super.plus(vector).toMutableVector2()
  
  operator fun minus(vector: Vector2<NumberType>) = super.minus(vector).toMutableVector2()

  override operator fun times(scalar: NumberType) = super.times(scalar).toMutableVector2()

  override operator fun div(scalar: NumberType) = super.div(scalar).toMutableVector2()

  override fun normalizeFloat() = super.normalizeFloat().toMutableVector2()

  override fun normalizeDouble() = super.normalizeDouble().toMutableVector2()
}