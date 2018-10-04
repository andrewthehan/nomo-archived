package com.github.andrewthehan.nomo.util.math

open class Vector3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3<Float>(x, y, z)
open class Vector3i(x: Int = 0, y: Int = 0, z: Int = 0) : Vector3<Int>(x, y, z)

fun <NumberType : Number> Vector<NumberType>.toVector3(): Vector3<NumberType> {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return Vector3(this[0], this[1], this[2])
}

operator fun <NumberType : Number> NumberType.times(vector: Vector3<NumberType>)
  = times(vector as Vector<NumberType>).toVector3()

operator fun <NumberType : Number> NumberType.div(vector: Vector3<NumberType>)
  = div(vector as Vector<NumberType>).toVector3()

open class Vector3<NumberType : Number>(x: NumberType = zero<NumberType>(), y: NumberType = zero<NumberType>(), z: NumberType = zero<NumberType>()) : Vector<NumberType>(x, y, z) {
  val x: NumberType
    get() = this[0]
      
  val y: NumberType
    get() = this[1]

  val z: NumberType
    get() = this[2]

  operator fun component1() = this[0]
  
  operator fun component2() = this[1]

  operator fun component3() = this[2]

  operator fun plus(vector: Vector3<NumberType>) = super.plus(vector).toVector3()
  
  operator fun minus(vector: Vector3<NumberType>) = super.minus(vector).toVector3()

  override operator fun times(scalar: NumberType) = super.times(scalar).toVector3()

  override operator fun div(scalar: NumberType) = super.div(scalar).toVector3()

  override fun normalizeFloat() = super.normalizeFloat().toVector3()

  override fun normalizeDouble() = super.normalizeDouble().toVector3()
}