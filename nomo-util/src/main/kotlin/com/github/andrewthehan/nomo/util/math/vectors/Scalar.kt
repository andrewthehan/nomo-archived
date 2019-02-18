package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

fun zeroScalarF() = zeroVector1f()
fun zeroScalarI() = zeroVector1i()

fun scalarOf(x: Float) = vectorOf(x)
fun scalarOf(x: Int) = vectorOf(x)

typealias ScalarF = Vector1f
typealias ScalarI = Vector1i

typealias Scalar<NumberType, VectorType> = Vector1<NumberType, VectorType>

fun <NumberType : Number> Scalar<NumberType, *>.toNumber(): NumberType {
  return this[0]
}

operator fun <NumberType : Number> Scalar<NumberType, *>.plus(number: NumberType): NumberType {
  return this[0] + number
}

operator fun <NumberType : Number> Scalar<NumberType, *>.minus(number: NumberType): NumberType {
  return this[0] - number
}

operator fun <NumberType : Number> NumberType.plus(scalar: Scalar<NumberType, *>): NumberType {
  return scalar[0] + this
}

operator fun <NumberType : Number> NumberType.minus(scalar: Scalar<NumberType, *>): NumberType {
  return scalar[0] - this
}

operator fun <NumberType : Number, VectorType : Vector<NumberType, VectorType>> VectorType.times(scalar: Scalar<NumberType, *>): VectorType {
  return this * scalar[0]
}

operator fun <NumberType : Number, VectorType : Vector<NumberType, VectorType>> VectorType.div(scalar: Scalar<NumberType, *>): VectorType {
  return this / scalar[0]
}