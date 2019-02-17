package com.github.andrewthehan.nomo.util.math.vectors

fun zeroVector3f() = Vector3fInternal(0f, 0f, 0f)
fun zeroVector3i() = Vector3iInternal(0, 0, 0)

fun vectorOf(x: Float, y: Float, z: Float) = Vector3fInternal(x, y, z)
fun vectorOf(x: Int, y: Int, z: Int) = Vector3iInternal(x, y, z)

class Vector3fInternal(x: Float, y: Float, z: Float) : AbstractVector<Float, Vector3f>(x, y, z), Vector3f {
  override fun create(elementProvider: (Int) -> Float) = Vector3fInternal(elementProvider(0), elementProvider(1), elementProvider(2))
}

class Vector3iInternal(x: Int, y: Int, z: Int) : AbstractVector<Int, Vector3i>(x, y, z), Vector3i {
  override fun create(elementProvider: (Int) -> Int) = Vector3iInternal(elementProvider(0), elementProvider(1), elementProvider(2))
}

interface Vector3f : Vector3<Float, Vector3f>
interface Vector3i : Vector3<Int, Vector3i>

interface Vector3<NumberType : Number, VectorType : Vector3<NumberType, VectorType>> : Vector<NumberType, VectorType> { 
  val x: NumberType
    get() = this[0]
  val y: NumberType
    get() = this[1]
  val z: NumberType
    get() = this[2]

  operator fun component1() = this[0]
  operator fun component2() = this[1]
  operator fun component3() = this[2]
}
