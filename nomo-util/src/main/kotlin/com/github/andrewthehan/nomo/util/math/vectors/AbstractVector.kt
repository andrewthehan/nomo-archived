package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

import kotlin.math.sqrt

abstract class AbstractVector<NumberType : Number, VectorType : Vector<NumberType, VectorType>> : Vector<NumberType, VectorType> {
  override val dimensions: Int
  override val components: List<NumberType>

  constructor(vararg elements: NumberType) {
    this.dimensions = elements.size
    this.components = List(this.dimensions) { elements[it] }
  }
  
  override operator fun unaryPlus() = vectorTypeOf { this[it] }
  override operator fun unaryMinus() = vectorTypeOf { -this[it] }
  override operator fun plus(vector: Vector<NumberType, *>) = binaryOperation(vector) { a, b -> a + b }
  override operator fun minus(vector: Vector<NumberType, *>) = binaryOperation(vector) { a, b -> a - b }
  override operator fun times(scalar: NumberType) = binaryOperation(scalar) { a, b -> a * b }
  override operator fun div(scalar: NumberType) = binaryOperation(scalar) { a, b -> a / b }
  override infix fun dot(vector: Vector<NumberType, *>): NumberType {
    require(dimensions == vector.dimensions) { "Dot product only applies to two vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    return binaryOperation(vector) { a, b -> a * b }.components.sum()
  }

  override fun length(): NumberType = sqrt(map { it * it }.components.sum().toFloat()).cast<NumberType>()
  override fun normalized(): VectorType {
    val length = length()
    return map { it / length }
  }

  override operator fun get(i: Int): NumberType = components[i]
  
  override fun map(transform: (NumberType) -> NumberType): VectorType = vectorTypeOf { transform(components[it]) }

  abstract fun vectorTypeOf(init: (Int) -> NumberType): VectorType
  
  private fun binaryOperation(vector: Vector<NumberType, *>, operation: (NumberType, NumberType) -> NumberType): VectorType {
    require(dimensions == vector.dimensions) { "Arithmetic operations on two vectors requires vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    return vectorTypeOf { operation(components[it], vector.components[it]) }
  }
  
  private fun binaryOperation(scalar: NumberType, operation: (NumberType, NumberType) -> NumberType): VectorType {
    return vectorTypeOf { operation(components[it], scalar) }
  }

  override fun toString() = "<" + components.joinToString() + ">"
}