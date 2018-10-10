package com.github.andrewthehan.nomo.util.math.vectors

interface MutableVector<NumberType : Number, VectorType : Vector<NumberType, VectorType>> : Vector<NumberType, VectorType> {
  override val components: MutableList<NumberType>
  
  operator fun plusAssign(vector: Vector<NumberType, *>): Unit
  operator fun minusAssign(vector: Vector<NumberType, *>): Unit
  operator fun timesAssign(scalar: NumberType): Unit
  operator fun divAssign(scalar: NumberType): Unit

  fun set(vector: Vector<NumberType, *>): Unit
  operator fun set(i: Int, value: NumberType): Unit
}

// fun <NumberType : NumberType, VectorType> Vector<NumberType, VectorType>.toMutable(): MutableVector<NumberType, VectorType> 