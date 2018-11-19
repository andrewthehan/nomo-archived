package com.github.andrewthehan.nomo.boot.collision.ecs.systems

import com.github.andrewthehan.nomo.boot.collision.ecs.events.CollisionEvent
import com.github.andrewthehan.nomo.boot.collision.detectors.CollisionDetector
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

class CollisionDetectionSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @MutableInject
  lateinit var eventManager: EventManager

  private val collisionDetectors = mutableListOf<CollisionDetector>()

  fun addCollisionDetector(collisionDetector: CollisionDetector) {
    collisionDetectors.add(collisionDetector)
  }

  fun isColliding(aEntity: Entity, bEntity: Entity): Boolean {
    return collisionDetectors.isNotEmpty() && collisionDetectors.all { it.isColliding(aEntity, bEntity, entityComponentManager) }
  }

  fun collide(aEntity: Entity, bEntity: Entity) {
    val collisionEvent = CollisionEvent(aEntity, bEntity)
    eventManager.dispatchEvent(collisionEvent, setOf(aEntity, bEntity))
  }

  override fun update(delta: Float) {
    val collidables = entityComponentManager.getComponents<CollidableAttribute>()
    val entities = collidables.flatMap { entityComponentManager.getEntities(it) }
    (0 until entities.size).forEach { i ->
      val aEntity = entities[i]
      ((i + 1) until entities.size).forEach { j ->
        val bEntity = entities[j]
        if (isColliding(aEntity, bEntity)) {
          collide(aEntity, bEntity)
        }
      }
    }
  }
}