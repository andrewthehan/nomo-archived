package com.github.andrewthehan.nomo.boot.util.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.util.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior

abstract class DelayedBehavior(val updateDelay: Float) : AbstractBehavior() {
  private var timeElapsedSinceUpdate: Float = 0f

  @EventListener
  fun update(event: UpdateEvent) {
    timeElapsedSinceUpdate += event.delta
    if (timeElapsedSinceUpdate > updateDelay) {
      trigger()
    }
  }

  abstract fun trigger()
}