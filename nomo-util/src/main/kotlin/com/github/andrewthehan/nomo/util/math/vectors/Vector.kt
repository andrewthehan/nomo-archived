package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

import kotlin.math.sqrt

abstract class AbstractVector<NumberType : Number, VectorType : Vector<NumberType, VectorType>>: Vector<NumberType, VectorType>{
  override val dimensions: Int
  override val components: List<NumberType>

  constructor(vararg elements: NumberType) {
    this.dimensions = elements.size
    this.components = List(this.dimensions) { elements[it] }
  }

  override fun toString() = "<" + components.joinToString() + ">"
}

interface Vector<NumberType : Number, VectorType : Vector<NumberType, VectorType>> {
  val dimensions: Int
  val components: List<NumberType>

  fun create(elementProvider: (Int) -> NumberType): VectorType
  fun create(vararg elements: NumberType): VectorType = create { elements[it] }
  fun create(elements: List<NumberType>): VectorType = create { elements[it] }

  operator fun unaryPlus(): VectorType = create { this[it] }
  operator fun unaryMinus(): VectorType = create { -this[it] }
  operator fun plus(vector: VectorType): VectorType = create(components.zip(vector.components) { a, b -> a + b })
  operator fun minus(vector: VectorType): VectorType = create(components.zip(vector.components) { a, b -> a - b })
  operator fun times(scalar: NumberType): VectorType = map { it * scalar }
  operator fun div(scalar: NumberType): VectorType = map { it / scalar }
  infix fun dot(vector: VectorType): NumberType = components.zip(vector.components) { a, b -> a * b }.sum()

  operator fun get(i: Int): NumberType = components[i]

  fun length(): Float = sqrt(map { it * it }.components.sum().toFloat())
  fun normalized(): VectorType {
    val length = length().cast<NumberType>()
    return map { it / length }
  }

  fun isZero(): Boolean = components.all { it.isZero() }

  fun map(transform: (NumberType) -> NumberType): VectorType = create { transform(this[it]) }
}

operator fun <NumberType : Number, VectorType : Vector<NumberType, VectorType>> NumberType.times(vector: VectorType): VectorType {
  return vector.map { this * it }
}

operator fun <NumberType : Number, VectorType : Vector<NumberType, VectorType>> NumberType.div(vector: VectorType): VectorType {
  return vector.map { this / it }
}