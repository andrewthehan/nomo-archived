package com.github.andrewthehan.nomo.util.math

interface MutableVector<NumberType : Number, VectorType> : Vector<NumberType, VectorType> {
  override val components: MutableList<NumberType>
  
  operator fun plusAssign(vector: VectorType): Unit
  operator fun minusAssign(vector: VectorType): Unit
  operator fun timesAssign(scalar: NumberType): Unit
  operator fun divAssign(scalar: NumberType): Unit

  fun set(vector: VectorType): Unit
  operator fun set(i: Int, value: NumberType): Unit
}