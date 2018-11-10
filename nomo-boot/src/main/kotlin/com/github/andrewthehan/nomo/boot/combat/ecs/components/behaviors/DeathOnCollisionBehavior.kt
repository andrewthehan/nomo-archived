package com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.combat.ecs.events.DeathEvent
import com.github.andrewthehan.nomo.boot.physics.ecs.events.CollisionEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager

class DeathOnCollisionBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  fun onCollide(@Suppress("UNUSED_PARAMETER") event: CollisionEvent) {
    entityComponentManager[this].forEach {
      eventManager.dispatchEvent(DeathEvent(it), it)
    }
  }
}