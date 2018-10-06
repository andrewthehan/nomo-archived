package com.github.andrewthehan.nomo.boot.util.ecs.systems

import com.github.andrewthehan.nomo.boot.util.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

class UpdateSystem : AbstractSystem() {
  @MutableInject
  lateinit var eventManager: EventManager

  override fun update(delta: Float) {
    eventManager.dispatchEvent(UpdateEvent(delta))
  }
}