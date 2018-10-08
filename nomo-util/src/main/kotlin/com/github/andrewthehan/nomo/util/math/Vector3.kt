package com.github.andrewthehan.nomo.util.math

/* Float */
operator fun Float.times(vector: Vector3f)
  = times(vector as Vector<Float>).toVector3f()
operator fun Float.div(vector: Vector3f)
  = div(vector as Vector<Float>).toVector3f()
fun Vector<*>.toVector3f(): Vector3f {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return Vector3f(this[0].toFloat(), this[1].toFloat(), this[2].toFloat())
}
open class Vector3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3<Float>(x, y, z) {
  operator fun plus(vector: Vector3f) = super.plus(vector).toVector3f()
  operator fun minus(vector: Vector3f) = super.minus(vector).toVector3f()
  override operator fun times(scalar: Float) = super.times(scalar).toVector3f()
  override operator fun div(scalar: Float) = super.div(scalar).toVector3f()
}

/* Int */
operator fun Int.times(vector: Vector3i)
  = times(vector as Vector<Int>).toVector3i()
operator fun Int.div(vector: Vector3i)
  = div(vector as Vector<Int>).toVector3i()
fun Vector<*>.toVector3i(): Vector3i {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return Vector3i(this[0].toInt(), this[1].toInt(), this[2].toInt())
}
open class Vector3i(x: Int = 0, y: Int = 0, z: Int = 0) : Vector3<Int>(x, y, z) {
  operator fun plus(vector: Vector3i) = super.plus(vector).toVector3i()
  operator fun minus(vector: Vector3i) = super.minus(vector).toVector3i()
  override operator fun times(scalar: Int) = super.times(scalar).toVector3i()
  override operator fun div(scalar: Int) = super.div(scalar).toVector3i()
}

/* Base */
operator fun <NumberType : Number> NumberType.times(vector: Vector3<NumberType>)
  = times(vector as Vector<NumberType>).toVector3()
operator fun <NumberType : Number> NumberType.div(vector: Vector3<NumberType>)
  = div(vector as Vector<NumberType>).toVector3()
fun <NumberType : Number> Vector<NumberType>.toVector3(): Vector3<NumberType> {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return Vector3(this[0], this[1], this[2])
}
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