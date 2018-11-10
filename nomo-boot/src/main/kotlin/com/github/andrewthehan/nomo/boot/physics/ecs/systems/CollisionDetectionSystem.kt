package com.github.andrewthehan.nomo.boot.physics.ecs.systems

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.events.CollisionEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*

class CollisionDetectionSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @MutableInject
  lateinit var eventManager: EventManager
  
  // simple AABB collision
  fun collides(aPosition: MutableVector2f, aShape: Shape<Vector2f>, bPosition: MutableVector2f, bShape: Shape<Vector2f>): Boolean {
    val aPoints = aShape.points.map { it + aShape.center + aPosition }
    val bPoints = bShape.points.map { it + bShape.center + bPosition }

    val aMin = Vector2f(aPoints.map { it.x }.min()!!, aPoints.map { it.y }.min()!!)
    val aMax = Vector2f(aPoints.map { it.x }.max()!!, aPoints.map { it.y }.max()!!)
    val bMin = Vector2f(bPoints.map { it.x }.min()!!, bPoints.map { it.y }.min()!!)
    val bMax = Vector2f(bPoints.map { it.x }.max()!!, bPoints.map { it.y }.max()!!)

    // minkowski difference
    val mdMin = Vector2f(aMin.x - bMax.x, aMin.y - bMax.y)
    val mdMax = Vector2f(aMax.x - bMin.x, aMax.y - bMin.y)

    return mdMin.x <= 0 && mdMin.y <= 0 && mdMax.x >= 0 && mdMax.y >= 0
  }

  fun isColliding(aPosition: MutableVector2f, aShapes: Iterable<Shape<Vector2f>>, bPosition: MutableVector2f, bShapes: Iterable<Shape<Vector2f>>): Boolean {
    aShapes.forEach { aShape ->
      bShapes.forEach { bShape ->
        if (collides(aPosition, aShape, bPosition, bShape)) {
          return true
        }
      }
    }
    return false
  }

  override fun update(delta: Float) {
    val collidables = entityComponentManager.getComponents<CollidableAttribute>()
    val entities = collidables.flatMap { entityComponentManager.getEntities(it) }
    val entityToPositionMap = entities.associateBy({ it }, { entityComponentManager.getComponent<Position2dAttribute>(it) })
    val entityToShapesMap = entities.associateBy({ it }, { entityComponentManager.getComponents<Shape2fAttribute>(it) })
    (0 until entities.size).forEach { i ->
      val aEntity = entities[i]
      val aPosition = entityToPositionMap.getValue(aEntity)
      val aShapes = entityToShapesMap.getValue(aEntity)
      ((i + 1) until entities.size).forEach { j ->
        val bEntity = entities[j]
        val bPosition = entityToPositionMap.getValue(bEntity)
        val bShapes = entityToShapesMap.getValue(bEntity)
        if (isColliding(aPosition, aShapes, bPosition, bShapes)) {
          val collisionEvent = CollisionEvent(aEntity, bEntity)
          eventManager.dispatchEvent(collisionEvent, setOf(aEntity, bEntity))
        }
      }
    }
  }
}