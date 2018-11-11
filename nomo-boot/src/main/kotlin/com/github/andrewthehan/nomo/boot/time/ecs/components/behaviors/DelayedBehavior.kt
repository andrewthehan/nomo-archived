package com.github.andrewthehan.nomo.boot.time.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.time.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.boot.time.util.Timer
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior

abstract class DelayedBehavior(val updateDelay: Float) : AbstractBehavior() {
  private var triggered = false
  private val timer = Timer(updateDelay)

  @EventListener
  fun update(event: UpdateEvent) {
    if (triggered) {
      return
    }

    timer.update(event.delta)
    if (timer.shouldTrigger()) {
      triggered = true
      trigger()
    }
  }

  abstract fun trigger()
}