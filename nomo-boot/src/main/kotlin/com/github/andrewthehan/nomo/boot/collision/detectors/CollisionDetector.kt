package com.github.andrewthehan.nomo.boot.collision.detectors

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager

interface CollisionDetector {
  fun isColliding(aEntity: Entity, bEntity: Entity, entityComponentManager: EntityComponentManager): Boolean
}