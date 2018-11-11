package com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.*
import com.github.andrewthehan.nomo.boot.time.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.vectors.*

class FollowBehavior(var target: Entity, var speed: Float) : AbstractBehavior(), Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  fun follow(@Suppress("UNUSED_PARAMETER") event: UpdateEvent) {
    val entity = entityComponentManager[this]
    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)
    val velocity = entityComponentManager.getComponent<Velocity2dAttribute>(entity)

    if (!entityComponentManager.containsEntity(target)) {
      velocity.set(Vector2f(0f, 0f))
      return 
    }
    
    val targetPosition = entityComponentManager.getComponent<Position2dAttribute>(target)

    val distance = targetPosition - position
    val length = distance.length()
    if (length == 0f) {
      return
    }

    val direction = distance.map { it / length }
    velocity.set(speed * direction)
  }
}