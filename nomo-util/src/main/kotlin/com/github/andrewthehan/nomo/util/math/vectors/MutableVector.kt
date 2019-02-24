package com.github.andrewthehan.nomo.util.math.vectors

import com.github.andrewthehan.nomo.util.math.*

interface MutableVector<NumberType : Number> : Vector<NumberType> {
  override val components: MutableList<NumberType>
}
  
operator fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.plusAssign(vector: Vector<NumberType>) = (0 until dimensions).forEach { this[it] = this[it] + vector[it] }
operator fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.minusAssign(vector: Vector<NumberType>) = (0 until dimensions).forEach { this[it] = this[it] - vector[it] }
operator fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.timesAssign(scalar: NumberType) = mutableMap { it * scalar }
operator fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.divAssign(scalar: NumberType) = mutableMap { it / scalar }

fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.set(vector: Vector<NumberType>) = (0 until dimensions).forEach { this[it] = vector[it] }
operator fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.set(i: Int, value: NumberType) {
  components[i] = value
}

fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.zero(): Unit {
  // TODO: bug with `mutableMap { zero<NumberType>() }` returning an unexpected NumberType
  this -= this
}

fun <NumberType : Number, MutableVectorType : MutableVector<NumberType>> MutableVectorType.mutableMap(transform: (NumberType) -> NumberType) {
  (0 until dimensions).forEach {
    this[it] = transform(this[it])
  }
}
