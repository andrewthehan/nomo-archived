package com.github.andrewthehan.nomo.util.math.shapes

import com.github.andrewthehan.nomo.util.math.vectors.Vector

interface Shape<VectorType : Vector<*, *>> {
  val points: List<VectorType>
  val center: VectorType
}