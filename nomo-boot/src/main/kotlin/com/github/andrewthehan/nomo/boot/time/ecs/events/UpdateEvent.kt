package com.github.andrewthehan.nomo.boot.time.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Event

data class UpdateEvent(val delta: Float) : Event