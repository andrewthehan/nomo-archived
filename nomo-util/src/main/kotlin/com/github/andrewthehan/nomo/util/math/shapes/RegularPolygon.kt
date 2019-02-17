package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.math.*

import kotlin.math.*

fun rootsOfUnity(n: Int): List<Vector2f> {
  return (0 until n).map { vectorOf(cos(it * 2 * PI / n).toFloat(), sin(it * 2 * PI / n).toFloat()) }
}

open class RegularPolygon : Shape {
  override val points: List<Vector2f>
  override val center: Vector2f

  val radius: Float

  constructor(center: Vector2f, radius: Float, n: Int) {
    this.points = rootsOfUnity(n).map { point -> 
      point * radius + center
    }
    this.center = center
    this.radius = radius
  }
}