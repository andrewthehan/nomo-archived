package com.github.andrewthehan.nomo.util.math.vectors

fun zeroMutableVector3f() = MutableVector3fInternal(0f, 0f, 0f)
fun zeroMutableVector3i() = MutableVector3iInternal(0, 0, 0)

fun mutableVectorOf(x: Float, y: Float, z: Float) = MutableVector3fInternal(x, y, z)
fun mutableVectorOf(x: Int, y: Int, z: Int) = MutableVector3iInternal(x, y, z)

class MutableVector3fInternal(x: Float, y: Float, z: Float) : AbstractMutableVector<Float, Vector3f>(x, y, z), MutableVector3f {
  override fun create(elementProvider: (Int) -> Float) = MutableVector3fInternal(elementProvider(0), elementProvider(1), elementProvider(2))
}

class MutableVector3iInternal(x: Int, y: Int, z: Int) : AbstractMutableVector<Int, Vector3i>(x, y, z), MutableVector3i {
  override fun create(elementProvider: (Int) -> Int) = MutableVector3iInternal(elementProvider(0), elementProvider(1), elementProvider(2))
}

interface MutableVector3f : Vector3f, MutableVector3<Float, Vector3f>
interface MutableVector3i : Vector3i, MutableVector3<Int, Vector3i>

interface MutableVector3<NumberType : Number, VectorType : Vector3<NumberType, VectorType>> : Vector3<NumberType, VectorType>, MutableVector<NumberType, VectorType> {
  override var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }
  override var y: NumberType
    get() = this[1]
    set(value: NumberType) {
      this[1] = value
    }
  override var z: NumberType
    get() = this[2]
    set(value: NumberType) {
      this[2] = value
    }
}