package com.github.andrewthehan.nomo.util.math.vectors

fun zeroMutableVector2f() = MutableVector2fInternal(0f, 0f)
fun zeroMutableVector2i() = MutableVector2iInternal(0, 0)

fun mutableVectorOf(x: Float, y: Float) = MutableVector2fInternal(x, y)
fun mutableVectorOf(x: Int, y: Int) = MutableVector2iInternal(x, y)

class MutableVector2fInternal(x: Float, y: Float) : AbstractMutableVector<Float, Vector2f>(x, y), MutableVector2f {
  override fun create(elementProvider: (Int) -> Float) = MutableVector2fInternal(elementProvider(0), elementProvider(1))
}

class MutableVector2iInternal(x: Int, y: Int) : AbstractMutableVector<Int, Vector2i>(x, y), MutableVector2i {
  override fun create(elementProvider: (Int) -> Int) = MutableVector2iInternal(elementProvider(0), elementProvider(1))
}

interface MutableVector2f : Vector2f, MutableVector2<Float, Vector2f>
interface MutableVector2i : Vector2i, MutableVector2<Int, Vector2i>

interface MutableVector2<NumberType : Number, VectorType : Vector2<NumberType, VectorType>> : Vector2<NumberType, VectorType>, MutableVector<NumberType, VectorType> {
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
}