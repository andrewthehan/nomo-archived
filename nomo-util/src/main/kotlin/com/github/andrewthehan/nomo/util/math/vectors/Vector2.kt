package com.github.andrewthehan.nomo.util.math.vectors

fun zeroVector2f() = Vector2fInternal(0f, 0f)
fun zeroVector2i() = Vector2iInternal(0, 0)

fun vectorOf(x: Float, y: Float) = Vector2fInternal(x, y)
fun vectorOf(x: Int, y: Int) = Vector2iInternal(x, y)

class Vector2fInternal(x: Float, y: Float) : AbstractVector<Float, Vector2f>(x, y), Vector2f {
  override fun create(elementProvider: (Int) -> Float) = Vector2fInternal(elementProvider(0), elementProvider(1))
}

class Vector2iInternal(x: Int, y: Int) : AbstractVector<Int, Vector2i>(x, y), Vector2i {
  override fun create(elementProvider: (Int) -> Int) = Vector2iInternal(elementProvider(0), elementProvider(1))
}

interface Vector2f : Vector2<Float, Vector2f>
interface Vector2i : Vector2<Int, Vector2i>

interface Vector2<NumberType : Number, VectorType : Vector2<NumberType, VectorType>> : Vector<NumberType, VectorType> { 
  val x: NumberType
    get() = this[0]
  val y: NumberType
    get() = this[1]

  operator fun component1() = this[0]
  operator fun component2() = this[1]
}
