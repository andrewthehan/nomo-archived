package com.github.andrewthehan.nomo.util.math

abstract class AbstractMutableVector<NumberType : Number, VectorType : MutableVector<NumberType, *>> : AbstractVector<NumberType, VectorType>, MutableVector<NumberType, VectorType> {
  override val dimensions: Int
  override val components: MutableList<NumberType>

  constructor(vararg elements: NumberType): super(*elements) {
    this.dimensions = elements.size
    this.components = MutableList(this.dimensions) { elements[it] }
  }
  
  override operator fun plusAssign(vector: VectorType): Unit = mutableBinaryOperation(vector) { a, b -> a + b }
  override operator fun minusAssign(vector: VectorType): Unit = mutableBinaryOperation(vector) { a, b -> a - b }
  override operator fun timesAssign(scalar: NumberType): Unit = mutableBinaryOperation(scalar) { a, b -> a * b }
  override operator fun divAssign(scalar: NumberType): Unit = mutableBinaryOperation(scalar) { a, b -> a / b }

  override fun set(vector: VectorType): Unit {
    require(dimensions == vector.dimensions) { "Vector dimensions should match: ${dimensions} != ${vector.dimensions}" }
    (0 until dimensions).forEach {
      components[it] = vector.components[it]
    }
  }
  override operator fun set(i: Int, value: NumberType): Unit {
    components[i] = value
  }

  private fun mutableBinaryOperation(vector: VectorType, operation: (NumberType, NumberType) -> NumberType) {
    require(dimensions == vector.dimensions) { "Arithmetic operations on two vectors requires vectors of equal dimensions: ${dimensions} != ${vector.dimensions} "}
    (0 until dimensions).forEach {
      components[it] = operation(components[it], vector.components[it])
    }
  }

  private fun mutableBinaryOperation(scalar: NumberType, operation: (NumberType, NumberType) -> NumberType) {
    (0 until dimensions).forEach {
      components[it] = operation(components[it], scalar)
    }
  }

  fun zero() {
    (0 until dimensions).forEach {
      components[it] = zero<NumberType>()
    }
  }
}