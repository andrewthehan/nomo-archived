package com.github.andrewthehan.nomo.boot.physics.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.math.vectors.*

data class Force2dEvent(val newtons: Vector2f) : Event

data class Force3dEvent(val newtons: Vector3f) : Event