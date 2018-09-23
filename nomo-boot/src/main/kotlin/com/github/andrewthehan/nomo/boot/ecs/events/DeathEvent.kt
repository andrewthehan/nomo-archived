package com.github.andrewthehan.nomo.boot.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event

data class DeathEvent(val entity: Entity) : Event