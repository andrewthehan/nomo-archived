package com.github.andrewthehan.nomo.boot.collision.detectors

import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.boot.layer.ecs.components.attributes.LayerAttribute
import com.github.andrewthehan.nomo.boot.layer.Layer

class LayerCollisionDetector : CollisionDetector {
  override fun isColliding(aEntity: Entity, bEntity: Entity, entityComponentManager: EntityComponentManager): Boolean {
    val aLayers = entityComponentManager.getComponents<LayerAttribute>(aEntity).map { it.layer }
    val bLayers = entityComponentManager.getComponents<LayerAttribute>(bEntity).map { it.layer }

    return (aLayers.isEmpty() && bLayers.isEmpty()) || aLayers.any(bLayers::contains)
  }
}