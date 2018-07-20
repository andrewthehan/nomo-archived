package com.github.andrewthehan.nomo.core.ecs.managers

class EcsManager {
  val entityComponentManager = EntityComponentManager(this)
  val eventManager = EventManager(this)
  val metadataManager = MetadataManager(this)
}
