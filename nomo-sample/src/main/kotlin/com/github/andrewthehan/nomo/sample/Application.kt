package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.managers.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.sdk.ecs.entity.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*
import com.github.andrewthehan.nomo.sdk.ecs.systems.*

fun main(args: Array<String>) {
  println("Hello, world!")

  val ecsManager = EcsManager()
  ecsManager.systemManager.addSystem(UpdateSystem(ecsManager))

  createEntity(ecsManager)

  ecsManager.systemManager.update(0.001f)
  ecsManager.systemManager.update(0.001f)
  ecsManager.systemManager.update(0.001f)
  ecsManager.systemManager.update(0.001f)
  ecsManager.systemManager.update(0.001f)
}

fun createEntity(ecsManager: EcsManager) {
  entity(ecsManager.entityComponentManager) {
    + HealthAttribute(100f)
    + UpdateBehavior({
      val event = it
      val entityComponentManager = it.ecsManager.entityComponentManager
      entityComponentManager.getEntities(this)
        .map { entityComponentManager.getComponent<HealthAttribute<Float>>(it) }
        .forEach { 
          println(it.value)
          it.damage(event.delta)
        }
    })
  }
}
