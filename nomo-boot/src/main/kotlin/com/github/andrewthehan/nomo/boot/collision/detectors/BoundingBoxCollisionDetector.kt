package com.github.andrewthehan.nomo.boot.collision.detectors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.ShapeAttribute
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*

class BoundingBoxCollisionDetector : CollisionDetector {
  override fun isColliding(aEntity: Entity, bEntity: Entity, entityComponentManager: EntityComponentManager): Boolean {
    val aPosition = entityComponentManager.getComponent<Position2dAttribute>(aEntity)
    val bPosition = entityComponentManager.getComponent<Position2dAttribute>(bEntity)
    val aShapes = entityComponentManager.getComponents<ShapeAttribute>(aEntity)
    val bShapes = entityComponentManager.getComponents<ShapeAttribute>(bEntity)

    return isColliding(aPosition, aShapes, bPosition, bShapes)
  }

  // per shapes
  fun isColliding(aPosition: Vector2f, aShapes: Iterable<Shape>, bPosition: Vector2f, bShapes: Iterable<Shape>): Boolean {
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
  fun isColliding(aPosition: Vector2f, aShape: Shape, bPosition: Vector2f, bShape: Shape): Boolean {
    val aPoints = aShape.points.map { it + aShape.center + aPosition }
    val bPoints = bShape.points.map { it + bShape.center + bPosition }

    val aMin = vectorOf(aPoints.map { it.x }.min()!!, aPoints.map { it.y }.min()!!)
    val bMin = vectorOf(bPoints.map { it.x }.min()!!, bPoints.map { it.y }.min()!!)
    val aMax = vectorOf(aPoints.map { it.x }.max()!!, aPoints.map { it.y }.max()!!)
    val bMax = vectorOf(bPoints.map { it.x }.max()!!, bPoints.map { it.y }.max()!!)

    // minkowski difference
    val mdMin = vectorOf(aMin.x - bMax.x, aMin.y - bMax.y)
    val mdMax = vectorOf(aMax.x - bMin.x, aMax.y - bMin.y)

    return mdMin.x <= 0 && mdMin.y <= 0 && mdMax.x >= 0 && mdMax.y >= 0
  }
}