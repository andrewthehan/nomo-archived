package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.Vector2
import com.github.andrewthehan.nomo.util.math.*

open class Rectangle<NumberType : Number, VectorType : Vector2<NumberType, VectorType>> : Shape2<VectorType> {
  override val points: List<VectorType>
  override val center: VectorType

  constructor(center: VectorType, width: NumberType, height: NumberType) {
    @Suppress("UNCHECKED_CAST")
    val halfWidth = width / 2 as NumberType
    @Suppress("UNCHECKED_CAST")
    val halfHeight = height / 2 as NumberType
    this.points = listOf(
      center + center.vectorTypeOf { listOf(-halfWidth, -halfHeight)[it] },
      center + center.vectorTypeOf { listOf(halfWidth, -halfHeight)[it] },
      center + center.vectorTypeOf { listOf(halfWidth, halfHeight)[it] },
      center + center.vectorTypeOf { listOf(-halfWidth, halfHeight)[it] }
    )
    this.center = center
  }
}