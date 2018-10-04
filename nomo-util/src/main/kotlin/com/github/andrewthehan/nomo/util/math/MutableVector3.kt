package com.github.andrewthehan.nomo.util.math

open class MutableVector3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3<Float>(x, y, z)
open class MutableVector3i(x: Int = 0, y: Int = 0, z: Int = 0) : MutableVector3<Int>(x, y, z)

fun <NumberType : Number> Vector<NumberType>.toMutableVector3(): MutableVector3<NumberType> {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return MutableVector3(this[0], this[1], this[2])
}

operator fun <NumberType : Number> NumberType.times(vector: MutableVector3<NumberType>)
  = times(vector as Vector<NumberType>).toMutableVector3()

operator fun <NumberType : Number> NumberType.div(vector: MutableVector3<NumberType>)
  = div(vector as Vector<NumberType>).toMutableVector3()

open class MutableVector3<NumberType : Number>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>(), z: NumberType = zero<NumberType>()) : MutableVector<NumberType>(x, y, z) {
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
      
  var z: NumberType
    get() = this[2]
    set(value: NumberType) {
      this[2] = value
    }

  operator fun component1() = this[0]
  
  operator fun component2() = this[1]

  operator fun component3() = this[2]

  operator fun plus(vector: Vector3<NumberType>) = super.plus(vector).toMutableVector3()
  
  operator fun minus(vector: Vector3<NumberType>) = super.minus(vector).toMutableVector3()

  override operator fun times(scalar: NumberType) = super.times(scalar).toMutableVector3()

  override operator fun div(scalar: NumberType) = super.div(scalar).toMutableVector3()

  override fun normalizeFloat() = super.normalizeFloat().toMutableVector3()

  override fun normalizeDouble() = super.normalizeDouble().toMutableVector3()
}