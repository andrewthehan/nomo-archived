package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.Vector2f

open class Circle : RegularPolygon {
  constructor(center: Vector2f, radius: Float) : super(center, radius, 100)
}