package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

fun zeroMutableScalarF() = zeroMutableVector1f()
fun zeroMutableScalarI() = zeroMutableVector1i()

fun mutableScalarOf(x: Float) = mutableVectorOf(x)
fun mutableScalarOf(x: Int) = mutableVectorOf(x)

typealias MutableScalarF = MutableVector1f
typealias MutableScalarI = MutableVector1i

typealias MutableScalar<NumberType, VectorType> = MutableVector1<NumberType, VectorType>

fun <NumberType : Number> MutableScalar<NumberType, *>.set(number: NumberType): Unit {
  this[0] = number
}

operator fun <NumberType : Number> MutableScalar<NumberType, *>.plusAssign(number: NumberType): Unit {
  mutableMap { it + number }
}

operator fun <NumberType : Number> MutableScalar<NumberType, *>.minusAssign(number: NumberType): Unit {
  mutableMap { it - number }
}

operator fun <NumberType : Number, VectorType : MutableVector<NumberType, VectorType>> VectorType.timesAssign(scalar: Scalar<NumberType, *>): Unit {
  this *= scalar[0]
}

operator fun <NumberType : Number, VectorType : MutableVector<NumberType, VectorType>> VectorType.divAssign(scalar: Scalar<NumberType, *>): Unit {
  this /= scalar[0]
}