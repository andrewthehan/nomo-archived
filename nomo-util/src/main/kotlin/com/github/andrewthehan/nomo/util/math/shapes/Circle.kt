package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.Vector2
import com.github.andrewthehan.nomo.util.math.vectors.Vector2f

open class Circle<NumberType : Number, VectorType : Vector2<NumberType, VectorType>> : RegularPolygon<NumberType, VectorType> {
  constructor(center: VectorType, radius: NumberType) : super(center, radius, 100)
}