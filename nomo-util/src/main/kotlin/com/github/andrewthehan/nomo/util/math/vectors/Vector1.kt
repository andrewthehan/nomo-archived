package com.github.andrewthehan.nomo.util.math.vectors

fun zeroVector1f() = Vector1fInternal(0f)
fun zeroVector1i() = Vector1iInternal(0)

fun vectorOf(x: Float) = Vector1fInternal(x)
fun vectorOf(x: Int) = Vector1iInternal(x)

class Vector1fInternal(x: Float) : AbstractVector<Float, Vector1f>(x), Vector1f {
  override fun create(elementProvider: (Int) -> Float) = Vector1fInternal(elementProvider(0))
}

class Vector1iInternal(x: Int) : AbstractVector<Int, Vector1i>(x), Vector1i {
  override fun create(elementProvider: (Int) -> Int) = Vector1iInternal(elementProvider(0))
}

interface Vector1f : Vector1<Float, Vector1f>
interface Vector1i : Vector1<Int, Vector1i>

interface Vector1<NumberType : Number, VectorType : Vector1<NumberType, VectorType>> : Vector<NumberType, VectorType> { 
  val x: NumberType
    get() = this[0]

  operator fun component1() = this[0]
}
