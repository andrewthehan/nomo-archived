package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.Vector2
import com.github.andrewthehan.nomo.util.math.vectors.Vector2f
import com.github.andrewthehan.nomo.util.math.*

import kotlin.math.*

fun rootsOfUnity(n: Int): List<Vector2f> {
  return (0 until n).map { Vector2f(cos(it * 2 * PI / n).toFloat(), sin(it * 2 * PI / n).toFloat()) }
}

open class RegularPolygon<NumberType : Number, VectorType : Vector2<NumberType, VectorType>> : Shape2<VectorType> {
  override val points: List<VectorType>
  override val center: VectorType

  val radius: NumberType

  constructor(center: VectorType, radius: NumberType, n: Int) {
    this.points = rootsOfUnity(n).map { point -> 
      (center + center.vectorTypeOf { point.components[it].cast<NumberType>() }) * radius
    }
    this.center = center
    this.radius = radius
  }
}