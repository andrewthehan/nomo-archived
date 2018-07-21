package com.github.andrewthehan.nomo.sdk.ecs.systems

import com.github.andrewthehan.nomo.core.ecs.managers.EcsManager
import com.github.andrewthehan.nomo.sdk.ecs.events.UpdateEvent

class UpdateSystem(ecsManager: EcsManager) : AbstractSystem(ecsManager = ecsManager) {
  override fun update(delta: Float) = ecsManager.eventManager.dispatchEvent(UpdateEvent(ecsManager, delta))
}