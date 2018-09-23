package com.github.andrewthehan.nomo.boot.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.ecs.events.DeathEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager

class RemoveOnDeathBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(DeathEvent::class)
  fun onDeath(event: DeathEvent) {
    entityComponentManager.remove(event.entity)
  }
}