package com.github.andrewthehan.nomo.sample.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

import com.badlogic.gdx.math.Vector2

import ktx.math.*

open class Vector2Attribute(val vector: Vector2 = Vector2(0f, 0f)) : AbstractAttribute() {
  var x: Float
    get() = vector.x
    set(value: Float) {
      vector.x = value
    }
  var y: Float
    get() = vector.y
    set(value: Float) {
      vector.y = value
    }
    
  fun isZero() = x == 0f && y == 0f

  operator fun plusAssign(v: Vector2) {
    vector += v
  }

  fun plus(x: Float, y: Float) =
    vector.add(x, y)
  
  fun set(v: Vector2) {
    vector.set(v)
  }

  fun set(x: Float, y: Float) {
    vector.set(x, y)
  }

  fun reset() {
    vector.setZero()
  }
}