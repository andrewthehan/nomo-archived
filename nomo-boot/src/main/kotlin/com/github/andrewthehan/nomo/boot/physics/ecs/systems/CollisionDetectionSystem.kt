package com.github.andrewthehan.nomo.boot.physics.ecs.systems

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.events.CollisionEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem
import com.github.andrewthehan.nomo.util.math.vectors.*

class CollisionDetectionSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @MutableInject
  lateinit var eventManager: EventManager
  
  infix fun <NumberType : Number, VectorType : Vector<NumberType, VectorType>> Iterable<VectorType>.collides(other: Iterable<VectorType>): Boolean {
    return any { a -> other.any { (a - it).lengthFloat() < 10f } }
  }

  fun <NumberType : Number, VectorType : Vector<NumberType, VectorType>> isColliding(aShapes: Iterable<Iterable<VectorType>>, bShapes: Iterable<Iterable<VectorType>>): Boolean {
    aShapes.forEach { a ->
      bShapes.forEach { b ->
        if (a collides b) {
          return true
        }
      }
    }
    return false
  }

  override fun update(delta: Float) {
    val collidables = entityComponentManager.getComponents<CollidableAttribute>()
    val entities = collidables.flatMap { entityComponentManager.getEntities(it) }
    val entityToShapesMap = entities.associateBy({ it }, {
      val position = entityComponentManager.getComponent<Position2dAttribute>(it)
      val shapes = entityComponentManager.getComponents<Shape2fAttribute>(it)
      shapes.map { it.points.map { it + position } }
    })
    (0 until entities.size).forEach { i ->
      val aEntity = entities[i]
      val aShapes = entityToShapesMap.getValue(aEntity)
      ((i + 1) until entities.size).forEach { j ->
        val bEntity = entities[j]
        val bShapes = entityToShapesMap.getValue(bEntity)
        if (isColliding(aShapes, bShapes)) {
          val collisionEvent = CollisionEvent(aEntity, bEntity)
          eventManager.dispatchEvent(collisionEvent, setOf(aEntity, bEntity))
        }
      }
    }
  }
}