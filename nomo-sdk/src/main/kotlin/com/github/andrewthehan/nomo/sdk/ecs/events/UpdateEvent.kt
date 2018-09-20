package com.github.andrewthehan.nomo.sdk.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Event

data class UpdateEvent(val delta: Float) : Event