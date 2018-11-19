package com.github.andrewthehan.nomo.sample.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event

data class RenderEvent(val camera: Entity) : Event