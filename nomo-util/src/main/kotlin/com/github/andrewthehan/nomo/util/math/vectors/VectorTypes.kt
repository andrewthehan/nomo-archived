package com.github.andrewthehan.nomo.util.math.vectors

fun zeroVector1f() = Vector1fInternal(0f)
fun zeroVector2f() = Vector2fInternal(0f, 0f)
fun zeroVector3f() = Vector3fInternal(0f, 0f, 0f)
fun zeroVector1i() = Vector1iInternal(0)
fun zeroVector2i() = Vector2iInternal(0, 0)
fun zeroVector3i() = Vector3iInternal(0, 0, 0)

fun vectorOf(x: Float) = Vector1fInternal(x)
fun vectorOf(x: Float, y: Float) = Vector2fInternal(x, y)
fun vectorOf(x: Float, y: Float, z: Float) = Vector3fInternal(x, y, z)
fun vectorOf(x: Int) = Vector1iInternal(x)
fun vectorOf(x: Int, y: Int) = Vector2iInternal(x, y)
fun vectorOf(x: Int, y: Int, z: Int) = Vector3iInternal(x, y, z)

interface Vector1f : Vector1<Float>
interface Vector2f : Vector2<Float>
interface Vector3f : Vector3<Float>

interface Vector1i : Vector1<Int>
interface Vector2i : Vector2<Int>
interface Vector3i : Vector3<Int>

interface Vector1<NumberType : Number> : Vector<NumberType> { 
  val x: NumberType
    get() = this[0]

  operator fun component1() = this[0]
}

interface Vector2<NumberType : Number> : Vector<NumberType> { 
  val x: NumberType
    get() = this[0]
  val y: NumberType
    get() = this[1]

  operator fun component1() = this[0]
  operator fun component2() = this[1]
}

interface Vector3<NumberType : Number> : Vector<NumberType> { 
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

class Vector1fInternal(x: Float) : AbstractVector<Float>(x), Vector1f {
  override val vectorProducer: (elementProvider: (Int) -> Float) -> Vector1f = { Vector1fInternal(it(0)) }
}

class Vector2fInternal(x: Float, y: Float) : AbstractVector<Float>(x, y), Vector2f {
  override val vectorProducer: (elementProvider: (Int) -> Float) -> Vector2f = { Vector2fInternal(it(0), it(1)) }
}

class Vector3fInternal(x: Float, y: Float, z: Float) : AbstractVector<Float>(x, y, z), Vector3f {
  override val vectorProducer: (elementProvider: (Int) -> Float) -> Vector3f = { Vector3fInternal(it(0), it(1), it(2)) }
}

class Vector1iInternal(x: Int) : AbstractVector<Int>(x), Vector1i {
  override val vectorProducer: (elementProvider: (Int) -> Int) -> Vector1i = { Vector1iInternal(it(0)) }
}

class Vector2iInternal(x: Int, y: Int) : AbstractVector<Int>(x, y), Vector2i {
  override val vectorProducer: (elementProvider: (Int) -> Int) -> Vector2i = { Vector2iInternal(it(0), it(1)) }
}

class Vector3iInternal(x: Int, y: Int, z: Int) : AbstractVector<Int>(x, y, z), Vector3i {
  override val vectorProducer: (elementProvider: (Int) -> Int) -> Vector3i = { Vector3iInternal(it(0), it(1), it(2)) }
}
