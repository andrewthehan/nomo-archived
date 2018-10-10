package com.github.andrewthehan.nomo.boot.physics.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event

data class CollisionEvent(val a: Entity, val b: Entity) : Event {
  fun includes(entity: Entity): Boolean = a == entity || b == entity
}