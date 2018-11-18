package com.github.andrewthehan.nomo.boot.collision.detectors

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*

class BoundingBoxCollisionDetector : CollisionDetector {
  override fun isColliding(aEntity: Entity, bEntity: Entity, entityComponentManager: EntityComponentManager): Boolean {
    val aPosition = entityComponentManager.getComponent<Position2dAttribute>(aEntity)
    val bPosition = entityComponentManager.getComponent<Position2dAttribute>(bEntity)
    val aShapes = entityComponentManager.getComponents<Shape2fAttribute>(aEntity)
    val bShapes = entityComponentManager.getComponents<Shape2fAttribute>(bEntity)

    return isColliding(aPosition, aShapes, bPosition, bShapes)
  }

  // per shapes
  fun isColliding(aPosition: MutableVector2f, aShapes: Iterable<Shape<Vector2f>>, bPosition: MutableVector2f, bShapes: Iterable<Shape<Vector2f>>): Boolean {
    aShapes.forEach { aShape ->
      bShapes.forEach { bShape ->
        if (isColliding(aPosition, aShape, bPosition, bShape)) {
          return true
        }
      }
    }
    return false
  }

  // per shape
  fun isColliding(aPosition: MutableVector2f, aShape: Shape<Vector2f>, bPosition: MutableVector2f, bShape: Shape<Vector2f>): Boolean {
    val aPoints = aShape.points.map { it + aShape.center + aPosition }
    val bPoints = bShape.points.map { it + bShape.center + bPosition }

    val aMin = Vector2f(aPoints.map { it.x }.min()!!, aPoints.map { it.y }.min()!!)
    val bMin = Vector2f(bPoints.map { it.x }.min()!!, bPoints.map { it.y }.min()!!)
    val aMax = Vector2f(aPoints.map { it.x }.max()!!, aPoints.map { it.y }.max()!!)
    val bMax = Vector2f(bPoints.map { it.x }.max()!!, bPoints.map { it.y }.max()!!)

    // minkowski difference
    val mdMin = Vector2f(aMin.x - bMax.x, aMin.y - bMax.y)
    val mdMax = Vector2f(aMax.x - bMin.x, aMax.y - bMin.y)

    return mdMin.x <= 0 && mdMin.y <= 0 && mdMax.x >= 0 && mdMax.y >= 0
  }
}