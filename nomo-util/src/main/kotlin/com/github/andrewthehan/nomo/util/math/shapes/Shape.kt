package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.Vector2f

interface Shape {
  val points: List<Vector2f>
  val center: Vector2f
}