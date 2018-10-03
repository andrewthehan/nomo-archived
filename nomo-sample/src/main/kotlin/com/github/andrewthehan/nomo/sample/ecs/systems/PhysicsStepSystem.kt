package com.github.andrewthehan.nomo.sample.ecs.systems

import com.github.andrewthehan.nomo.sample.ecs.components.attributes.AccelerationAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.PositionAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.VelocityAttribute
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

import ktx.math.*

class PhysicsStepSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  override fun update(delta: Float) {
    val velocities = entityComponentManager.getComponents<VelocityAttribute>()
    velocities
      .filter { !it.isZero() }
      .forEach { velocity ->
        val entities = entityComponentManager.getEntities(velocity)
        entities.forEach { entity ->
          val position = entityComponentManager.getComponent<PositionAttribute>(entity)
          position += velocity.vector * delta
        }
      }

    val accelerations = entityComponentManager.getComponents<AccelerationAttribute>()
    accelerations
      .filter { !it.isZero() }
      .forEach { acceleration ->
        val entities = entityComponentManager.getEntities(acceleration)
        entities.forEach { entity ->
          val velocity = entityComponentManager.getComponent<VelocityAttribute>(entity)
          velocity += acceleration.vector * delta
        }
      }
  }
}