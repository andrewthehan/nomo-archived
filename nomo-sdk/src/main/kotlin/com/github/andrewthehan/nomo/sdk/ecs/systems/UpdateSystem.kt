package com.github.andrewthehan.nomo.sdk.ecs.systems

import com.github.andrewthehan.nomo.core.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.core.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.events.UpdateEvent

class UpdateSystem : AbstractSystem() {
  @MutableInject
  lateinit var eventManager: EventManager

  override fun update(delta: Float) {
    eventManager.dispatchEvent(UpdateEvent(delta))
  }
}