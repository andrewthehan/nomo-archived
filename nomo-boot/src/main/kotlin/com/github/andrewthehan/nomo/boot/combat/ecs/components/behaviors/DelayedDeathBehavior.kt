package com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.combat.ecs.events.DeathEvent
import com.github.andrewthehan.nomo.boot.util.ecs.components.behaviors.DelayedBehavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager

class DelayedDeathBehavior(updateDelay: Float) : DelayedBehavior(updateDelay) {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @MutableInject
  lateinit var eventManager: EventManager

  override fun trigger() {
    entityComponentManager[this].forEach {
      eventManager.dispatchEvent(DeathEvent(it))
    }
  }
}