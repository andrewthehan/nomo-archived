package com.github.andrewthehan.nomo.sdk.ecs.events

import com.github.andrewthehan.nomo.core.ecs.managers.EcsManager
import com.github.andrewthehan.nomo.core.ecs.types.Event

class UpdateEvent(ecsManager: EcsManager, val delta: Float) : Event(ecsManager)