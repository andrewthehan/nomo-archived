package com.github.andrewthehan.nomo.sample.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

import com.badlogic.gdx.math.Vector2

@Dependent(VelocityAttribute::class)
class AccelerationAttribute(vector: Vector2 = Vector2(0f, 0f)) : Vector2Attribute(vector), Exclusive {
  constructor(x: Float, y: Float) : this(Vector2(x, y))
}