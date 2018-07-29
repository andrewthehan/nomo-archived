package com.github.andrewthehan.nomo.sdk.ecs.entity;

import com.github.andrewthehan.nomo.core.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.util.randomId

fun entity(entityComponentManager: EntityComponentManager, init: EntityBuilder.() -> Unit): Entity {
  val entityBuilder = EntityBuilder()
  entityBuilder.init()
  entityBuilder.components.forEach { entityComponentManager.add(entityBuilder.id, it) }
  return entityBuilder.id
}

class EntityBuilder() {
  var id = randomId()
  val components = mutableListOf<Component>()

  operator fun Component.unaryPlus() = components.add(this)
}
