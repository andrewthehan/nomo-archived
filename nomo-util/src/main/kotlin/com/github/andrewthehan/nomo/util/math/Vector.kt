package com.github.andrewthehan.nomo.util.math

import kotlin.math.sqrt

operator fun <NumberType : Number> NumberType.times(vector: Vector<NumberType>) = vector * this

operator fun <NumberType : Number> NumberType.div(vector: Vector<NumberType>) = vector.map { this / it }

fun <NumberType : Number> vectorOf(vararg elements: NumberType) = Vector<NumberType>(*elements)

open class Vector<NumberType : Number>(val dimensions: Int, init: (Int) -> NumberType) {
  open val components = List<NumberType>(dimensions, init)

  constructor(vararg elements: NumberType) : this(elements.size, { elements[it] })
  
  operator fun unaryPlus() = Vector(dimensions) { this[it] }
  
  operator fun unaryMinus() = Vector(dimensions) { -this[it] }

  private fun binaryOperation(vector: Vector<NumberType>, operation: (NumberType, NumberType) -> NumberType): Vector<NumberType> {
    require(dimensions == vector.dimensions) { "Arithmetic operations on two vectors requires vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    return Vector(dimensions) { operation(
      components[it],
      vector.components[it]
    ) }
  }

  open operator fun plus(vector: Vector<NumberType>) = binaryOperation(vector, { a, b -> a + b })

  open operator fun minus(vector: Vector<NumberType>) = binaryOperation(vector, { a, b -> a - b })

  open operator fun times(scalar: NumberType) = map { it * scalar }

  open operator fun div(scalar: NumberType) = map { it / scalar }

  operator fun get(i: Int) = components[i]

  fun isZero() = (0 until dimensions).all { components[it].isZero() }

  open fun normalizeFloat(): Vector<Float> {
    val length = lengthFloat()
    return Vector(dimensions) { components[it].toFloat() / length }
  }

  open fun normalizeDouble(): Vector<Double> {
    val length = lengthDouble()
    return Vector(dimensions) { components[it].toDouble() / length }
  }

  fun lengthFloat(): Float = sqrt(map { it * it }.components.sum().toFloat())

  fun lengthDouble(): Double = sqrt(map { it * it }.components.sum().toDouble())

  infix fun dot(vector: Vector<NumberType>): NumberType {
    require(dimensions == vector.dimensions) { "Dot product only applies to two vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    return binaryOperation(vector, { a, b -> a * b }).components.sum()
  }

  fun forEach(action: (NumberType) -> Unit) {
    components.forEach(action)
  }

  open fun <R : Number> map(transform: (NumberType) -> R)
    = Vector(dimensions) { transform(components[it]) }

  infix fun equals(other: Vector<*>): Boolean {
    if (this === other) return true

    if (components != other.components) return false

    return true
  }

  override fun toString() = "<" + components.joinToString() + ">"
}