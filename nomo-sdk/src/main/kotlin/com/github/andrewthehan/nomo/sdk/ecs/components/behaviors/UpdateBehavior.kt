package com.github.andrewthehan.nomo.sdk.ecs.components.behaviors

import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.events.UpdateEvent

class UpdateBehavior(val onUpdate: UpdateBehavior.(UpdateEvent) -> Unit) : AbstractBehavior() {
  @EventListener(UpdateEvent::class)
  fun update(event: UpdateEvent) {
    onUpdate(event)
  }
}