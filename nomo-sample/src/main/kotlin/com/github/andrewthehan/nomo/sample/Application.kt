package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.managers.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.sdk.ecs.entity.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*
import com.github.andrewthehan.nomo.sdk.ecs.systems.*

fun main(args: Array<String>) {
  println("Hello, world!")

  val ecsEngine = EcsEngine()
  ecsEngine.addManager(DependencyInjectionManager(ecsEngine))
  ecsEngine.addManager(EntityComponentManager(ecsEngine))
  ecsEngine.addManager(EventManager(ecsEngine))
  ecsEngine.addManager(SystemManager(ecsEngine))

  createEntity(ecsEngine)

  val systemManager = ecsEngine.getManager<SystemManager>()!!
  systemManager.addSystem(UpdateSystem())

  // Simulate game loop
  for (i in 0..100) {
    systemManager.update(0.1f)
  }
}

fun createEntity(ecsEngine: EcsEngine) {
  val entityComponentManager = ecsEngine.getManager<EntityComponentManager>()!!
  entity(entityComponentManager) {
    + HealthAttribute(100f)
    + UpdateBehavior({
      val event = it
      entityComponentManager.getEntities(this)
        .map { entityComponentManager.getComponent<HealthAttribute<Float>>(it) }
        .forEach { 
          println(it.value)
          it.damage(event.delta)
        }
    })
  }
}
