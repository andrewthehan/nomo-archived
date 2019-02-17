package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

abstract class AbstractMutableVector<NumberType : Number, VectorType : Vector<NumberType, VectorType>>: AbstractVector<NumberType, VectorType>, MutableVector<NumberType, VectorType> {
  override val dimensions: Int
  override val components: MutableList<NumberType>

  constructor(vararg elements: NumberType) {
    this.dimensions = elements.size
    this.components = MutableList(this.dimensions) { elements[it] }
  }
}

interface MutableVector<NumberType : Number, VectorType : Vector<NumberType, VectorType>> : Vector<NumberType, VectorType> {

  override val components: MutableList<NumberType>
  
  operator fun plusAssign(vector: VectorType): Unit = (0 until dimensions).forEach { this[it] = this[it] + vector[it] }
  operator fun minusAssign(vector: VectorType): Unit = (0 until dimensions).forEach { this[it] = this[it] - vector[it] }
  operator fun timesAssign(scalar: NumberType): Unit = mutableMap { it * scalar }
  operator fun divAssign(scalar: NumberType): Unit = mutableMap { it / scalar }

  fun set(vector: VectorType): Unit = (0 until dimensions).forEach { this[it] = vector[it] }
  operator fun set(i: Int, value: NumberType): Unit {
    components[i] = value
  }

  fun zero(): Unit = mutableMap { zero<NumberType>() }

  fun mutableMap(transform: (NumberType) -> NumberType): Unit {
    (0 until dimensions).forEach {
      this[it] = transform(this[it])
    }
  }
}
