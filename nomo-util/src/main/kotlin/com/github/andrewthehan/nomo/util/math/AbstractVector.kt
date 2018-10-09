package com.github.andrewthehan.nomo.util.math

import kotlin.math.sqrt

abstract class AbstractVector<NumberType : Number, VectorType : Vector<NumberType, *>> : Vector<NumberType, VectorType> {
  override val dimensions: Int
  override val components: List<NumberType>

  constructor(vararg elements: NumberType) {
    this.dimensions = elements.size
    this.components = List(this.dimensions) { elements[it] }
  }
  
  override operator fun unaryPlus() = toVectorType { this[it] }
  override operator fun unaryMinus() = toVectorType { -this[it] }
  override operator fun plus(vector: Vector<NumberType, *>): VectorType = binaryOperation(vector) { a, b -> a + b }
  override operator fun minus(vector: Vector<NumberType, *>): VectorType = binaryOperation(vector) { a, b -> a - b }
  override operator fun times(scalar: NumberType): VectorType = binaryOperation(scalar) { a, b -> a * b }
  override operator fun div(scalar: NumberType): VectorType = binaryOperation(scalar) { a, b -> a / b }
  override infix fun dot(vector: Vector<NumberType, *>): NumberType {
    require(dimensions == vector.dimensions) { "Dot product only applies to two vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    return binaryOperation(vector) { a, b -> a * b }.components.sum()
  }

  override operator fun get(i: Int): NumberType = components[i]
  
  override fun map(transform: (NumberType) -> NumberType): VectorType = toVectorType { transform(components[it]) }

  abstract fun toVectorType(init: (Int) -> NumberType): VectorType
  
  private fun binaryOperation(vector: Vector<NumberType, *>, operation: (NumberType, NumberType) -> NumberType): VectorType {
    require(dimensions == vector.dimensions) { "Arithmetic operations on two vectors requires vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    return toVectorType { operation(components[it], vector.components[it]) }
  }
  
  private fun binaryOperation(scalar: NumberType, operation: (NumberType, NumberType) -> NumberType): VectorType {
    return toVectorType { operation(components[it], scalar) }
  }

  fun lengthFloat(): Float = sqrt(map { it * it }.components.sum().toFloat())
  fun lengthDouble(): Double = sqrt(map { it * it }.components.sum().toDouble())

  override fun toString() = "<" + components.joinToString() + ">"
}