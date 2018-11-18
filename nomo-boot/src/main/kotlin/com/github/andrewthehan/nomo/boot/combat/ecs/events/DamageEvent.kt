package com.github.andrewthehan.nomo.boot.combat.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event

data class DamageEvent(var amount: Float, val entities: Iterable<Entity>) : Event