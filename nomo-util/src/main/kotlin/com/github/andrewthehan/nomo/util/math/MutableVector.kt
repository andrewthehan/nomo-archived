package com.github.andrewthehan.nomo.util.math

operator fun <NumberType : Number> NumberType.times(vector: MutableVector<NumberType>) = vector * this

operator fun <NumberType : Number> NumberType.div(vector: MutableVector<NumberType>) = vector.map { this / it }

fun <NumberType : Number> mutableVectorOf(vararg elements: NumberType) = MutableVector<NumberType>(*elements)

open class MutableVector<NumberType : Number>(dimensions: Int, init: (Int) -> NumberType): Vector<NumberType>(dimensions, init) {
  override val components = super.components.toMutableList()

  constructor(vararg elements: NumberType) : this(elements.size, { elements[it] })

  private fun mutableBinaryOperation(vector: Vector<NumberType>, operation: (NumberType, NumberType) -> NumberType) {
    require(dimensions == vector.dimensions) { "Dot product only applies to two vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    (0 until dimensions).forEach {
      components[it] = operation(components[it], vector.components[it])
    }
  }

  operator fun plusAssign(vector: Vector<NumberType>) = mutableBinaryOperation(vector, { a, b  -> a + b })

  operator fun minusAssign(vector: Vector<NumberType>) = mutableBinaryOperation(vector, { a, b  -> a - b })

  open override operator fun times(scalar: NumberType): MutableVector<NumberType> = map { it * scalar }

  open override operator fun div(scalar: NumberType) = map { it / scalar }

  operator fun set(i: Int, value: NumberType) {
    components[i] = value
  }

  operator fun set(i: Int, j: Int, vector: Vector<NumberType>) {
    require(i <= j) { "Invalid range: [${i}, ${j})" }
    require(j - i == vector.dimensions) { "Vector dimensions should match range: range=[${i}, ${j}), dimensions=${vector.dimensions}" }
    (i until j).forEach {
      components[it] = vector[it]
    }
  }

  fun zero() {
    (0 until dimensions).forEach {
      components[it] = zero<NumberType>()
    }
  }
  
  open override fun <R : Number> map(transform: (NumberType) -> R)
    = MutableVector(dimensions) { transform(components[it]) }
}