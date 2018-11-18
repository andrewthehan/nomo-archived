package com.github.andrewthehan.nomo.boot.collision.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event

data class CollisionEvent(val aEntity: Entity, val bEntity: Entity) : Event {
  val entities = setOf(aEntity, bEntity)
  fun involves(entity: Entity): Boolean = aEntity == entity || bEntity == entity
}