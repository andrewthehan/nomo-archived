package com.github.andrewthehan.nomo.util.math

/* Float */
operator fun Float.times(vector: MutableVector3f)
  = times(vector as Vector<Float>).toMutableVector3f()
operator fun Float.div(vector: MutableVector3f)
  = div(vector as Vector<Float>).toMutableVector3f()
fun Vector<*>.toMutableVector3f(): MutableVector3f {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return MutableVector3f(this[0].toFloat(), this[1].toFloat(), this[2].toFloat())
}
open class MutableVector3f(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3<Float>(x, y, z) {
  override operator fun plus(vector: Vector<Float>) = super.plus(vector).toMutableVector3f()
  override operator fun minus(vector: Vector<Float>) = super.minus(vector).toMutableVector3f()
  override operator fun times(scalar: Float) = super.times(scalar).toMutableVector3f()
  override operator fun div(scalar: Float) = super.div(scalar).toMutableVector3f()
}

/* Int */
operator fun Int.times(vector: MutableVector3i)
  = times(vector as Vector<Int>).toMutableVector3i()
operator fun Int.div(vector: MutableVector3i)
  = div(vector as Vector<Int>).toMutableVector3i()
fun Vector<Int>.toMutableVector3i(): MutableVector3i {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return MutableVector3i(this[0].toInt(), this[1].toInt(), this[2].toInt())
}
open class MutableVector3i(x: Int = 0, y: Int = 0, z: Int = 0) : MutableVector3<Int>(x, y, z) {
  override operator fun plus(vector: Vector<Int>) = super.plus(vector).toMutableVector3i()
  override operator fun minus(vector: Vector<Int>) = super.minus(vector).toMutableVector3i()
  override operator fun times(scalar: Int) = super.times(scalar).toMutableVector3i()
  override operator fun div(scalar: Int) = super.div(scalar).toMutableVector3i()
}

operator fun <NumberType : Number> NumberType.times(vector: MutableVector3<NumberType>)
  = times(vector as Vector<NumberType>).toMutableVector3()
operator fun <NumberType : Number> NumberType.div(vector: MutableVector3<NumberType>)
  = div(vector as Vector<NumberType>).toMutableVector3()
fun <NumberType : Number> Vector<NumberType>.toMutableVector3(): MutableVector3<NumberType> {
  require(this.dimensions == 3) { "Vector dimensions should be 3: ${this.dimensions}" }
  return MutableVector3(this[0], this[1], this[2])
}
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

  override operator fun plus(vector: Vector<NumberType>) = super.plus(vector).toMutableVector3()
  override operator fun minus(vector: Vector<NumberType>) = super.minus(vector).toMutableVector3()
  override operator fun times(scalar: NumberType) = super.times(scalar).toMutableVector3()
  override operator fun div(scalar: NumberType) = super.div(scalar).toMutableVector3()
  override fun normalizeFloat() = super.normalizeFloat().toMutableVector3()
  override fun normalizeDouble() = super.normalizeDouble().toMutableVector3()
}