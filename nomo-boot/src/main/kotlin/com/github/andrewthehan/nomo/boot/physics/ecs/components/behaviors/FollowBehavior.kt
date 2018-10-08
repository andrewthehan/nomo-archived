package com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.util.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.*

class FollowBehavior(var target: Entity, var speed: Float) : AbstractBehavior(), Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  fun follow(@Suppress("UNUSED_PARAMETER") event: UpdateEvent) {
    val targetPosition = entityComponentManager.getComponent<Position2dAttribute>(target)

    val entity = entityComponentManager[this]
    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)
    val velocity = entityComponentManager.getComponent<Velocity2dAttribute>(entity)

    val distance = targetPosition - position

    if (distance.lengthFloat() == 0f) {
      return
    }

    val direction = distance.normalizeFloat()
    velocity.set(speed * direction)
  }
}