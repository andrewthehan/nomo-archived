package com.github.andrewthehan.nomo.util.math

interface Vector<NumberType : Number, VectorType> {
  val dimensions: Int
  val components: List<NumberType>

  operator fun unaryPlus(): VectorType
  operator fun unaryMinus(): VectorType
  operator fun plus(vector: Vector<NumberType, *>): VectorType
  operator fun minus(vector: Vector<NumberType, *>): VectorType
  operator fun times(scalar: NumberType): VectorType
  operator fun div(scalar: NumberType): VectorType
  infix fun dot(vector: Vector<NumberType, *>): NumberType

  operator fun get(i: Int): NumberType

  fun isZero(): Boolean = (0 until dimensions).all { components[it].isZero() }

  fun map(transform: (NumberType) -> NumberType): VectorType
}

operator fun <NumberType : Number, VectorTypeParameter, VectorType : Vector<NumberType, VectorTypeParameter>> NumberType.times(vector: VectorType): VectorTypeParameter {
  return vector.map { this * it }
}

operator fun <NumberType : Number, VectorTypeParameter, VectorType : Vector<NumberType, VectorTypeParameter>> NumberType.div(vector: VectorType): VectorTypeParameter {
  return vector.map { this / it }
}