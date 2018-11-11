package com.github.andrewthehan.nomo.boot.time.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.time.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.boot.time.util.Timer
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior

abstract class PeriodicBehavior(val updateDelay: Float) : AbstractBehavior() {
  private val timer = Timer(updateDelay)

  @EventListener
  fun update(event: UpdateEvent) {
    timer.update(event.delta)
    while (timer.shouldTrigger()) {
      trigger()
    }
  }

  abstract fun trigger()
}