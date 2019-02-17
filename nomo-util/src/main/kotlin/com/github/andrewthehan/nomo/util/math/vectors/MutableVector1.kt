package com.github.andrewthehan.nomo.util.math.vectors

fun zeroMutableVector1f() = MutableVector1fInternal(0f)
fun zeroMutableVector1i() = MutableVector1iInternal(0)

fun mutableVectorOf(x: Float) = MutableVector1fInternal(x)
fun mutableVectorOf(x: Int) = MutableVector1iInternal(x)

class MutableVector1fInternal(x: Float) : AbstractMutableVector<Float, Vector1f>(x), MutableVector1f {
  override fun create(elementProvider: (Int) -> Float) = MutableVector1fInternal(elementProvider(0))
}

class MutableVector1iInternal(x: Int) : AbstractMutableVector<Int, Vector1i>(x), MutableVector1i {
  override fun create(elementProvider: (Int) -> Int) = MutableVector1iInternal(elementProvider(0))
}

interface MutableVector1f : Vector1f, MutableVector1<Float, Vector1f>
interface MutableVector1i : Vector1i, MutableVector1<Int, Vector1i>

interface MutableVector1<NumberType : Number, VectorType : Vector1<NumberType, VectorType>> : Vector1<NumberType, VectorType>, MutableVector<NumberType, VectorType> {
  override open var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }
}