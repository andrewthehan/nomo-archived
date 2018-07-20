package com.github.andrewthehan.nomo.sdk.ecs.components.behaviors

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.events.UpdateEvent

class UpdateBehavior(val onUpdate: UpdateBehavior.(Long) -> Unit) : AbstractBehavior() {
  @EventListener(UpdateEvent::class)
  fun onUpdate(event: UpdateEvent) {
    this.onUpdate(event.delta)
  }
}