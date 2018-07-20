package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.managers.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.sdk.ecs.entity.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*

fun main(args: Array<String>) {
  println("Hello, world!")

  val ecsManager = EcsManager()
  val entityComponentManager = ecsManager.entityComponentManager

  entity(entityComponentManager) {
    + HealthAttribute(100)
    + UpdateBehavior({
      entityComponentManager.getEntities(this)
        .map { entityComponentManager.getComponent<HealthAttribute<Int>>(it) }
        .forEach { 
          println(it.value)
          it.damage(1)
        }
    })
  }
  
  entity(entityComponentManager) {
    + HealthAttribute(50f)
    + UpdateBehavior({
      entityComponentManager.getEntities(this)
        .map { entityComponentManager.getComponent<HealthAttribute<Float>>(it) }
        .forEach { 
          println(it.value)
          it.damage(2.3f)
        }
    })
  }

  val eventManager = ecsManager.eventManager
  
  eventManager.dispatchEvent(UpdateEvent(100))
  eventManager.dispatchEvent(UpdateEvent(100))
  eventManager.dispatchEvent(UpdateEvent(100))
  eventManager.dispatchEvent(UpdateEvent(100))
  eventManager.dispatchEvent(UpdateEvent(100))

  // val ecsManager = EcsManager()
  // val entityComponentManager = ecsManager.entityComponentManager

  // val entityId = entity(entityComponentManager) {
  //   + HealthAttribute(100)
  //   + NumberAttribute(55.5f)
  //   + NumberAttribute(22)
  // }

  // println("Entity: ${entityId}")
  // val healthAttributes = entityComponentManager.getComponents<HealthAttribute<Int>>(entityId).map({ it.value })
  // println("HealthAttribute<Int>: ${healthAttributes}")
  // val healthAttribute = entityComponentManager.getComponent<HealthAttribute<*>>(entityId).value
  // println("Single HealthAttribute<*>: ${healthAttribute}")

  // val numberAttributes = entityComponentManager.getComponents<NumberAttribute<Int>>(entityId).map({ it.value })
  // println("NumberAttribute<Int>: ${numberAttributes}")

  // val numberAttributesFloat = entityComponentManager.getComponents<NumberAttribute<Float>>(entityId).map({ it.value })
  // println("NumberAttribute<Float>: ${numberAttributesFloat}")

  // val numberAttributesWild = entityComponentManager.getComponents<NumberAttribute<*>>(entityId).map({ it.value })
  // println("NumberAttribute<*>: ${numberAttributesWild}")

  // val allComponents = entityComponentManager.getAllComponents(entityId)
  // println("All components: ${allComponents}")

}
