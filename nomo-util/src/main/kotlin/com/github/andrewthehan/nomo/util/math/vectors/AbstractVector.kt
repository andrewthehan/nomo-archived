package com.github.andrewthehan.nomo.util.math.vectors

abstract class AbstractVector<NumberType : Number> : Vector<NumberType> {
  override val dimensions: Int
  override val components: List<NumberType>

  constructor(vararg elements: NumberType) {
    this.dimensions = elements.size
    this.components = List(this.dimensions) { elements[it] }
  }

  override fun toString() = "<" + components.joinToString() + ">"
}