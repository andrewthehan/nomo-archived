package com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.util.math.vectors.*

class InverseQuadraticFollowBehavior(target: Entity, intensity: Float) : AbstractFollowBehavior(target, intensity) {
  override fun setVelocity(position: Vector2f, targetPosition: Vector2f) {
    val entity = entityComponentManager[this]
    val velocity = entityComponentManager.getComponent<Velocity2dAttribute>(entity)

    val distance = targetPosition - position
    velocity.set(intensity * distance)
  }
}