package com.github.andrewthehan.nomo.sdk.ecs.components.behaviors

import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.events.UpdateEvent

abstract class PeriodicBehavior(val updateDelay: Float) : AbstractBehavior() {
  private var timeElapsedSinceUpdate: Float = 0f

  @EventListener
  fun update(event: UpdateEvent) {
    timeElapsedSinceUpdate += event.delta
    if (timeElapsedSinceUpdate > updateDelay) {
      timeElapsedSinceUpdate -= updateDelay
      trigger()
    }
  }

  abstract fun trigger()
}