package com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.time.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.vectors.*

abstract class AbstractFollowBehavior(var target: Entity, var intensity: Float) : AbstractBehavior(), Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  fun follow(@Suppress("UNUSED_PARAMETER") event: UpdateEvent) {
    val entity = entityComponentManager[this]
    val velocity = entityComponentManager.getComponent<Velocity2dAttribute>(entity)

    if (!entityComponentManager.containsEntity(target)) {
      velocity.set(zeroVector2f())
      return
    }

    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)
    val targetPosition = entityComponentManager.getComponent<Position2dAttribute>(target)

    val distance = targetPosition - position
    val length = distance.length()
    if (length == 0f) {
      return
    }

    setVelocity(position, targetPosition)
  }

  abstract fun setVelocity(position: Vector2f, targetPosition: Vector2f);
}