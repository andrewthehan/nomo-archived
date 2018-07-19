package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.entity.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.HealthAttribute
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.NumberAttribute

fun main(args: Array<String>) {
  println("Hello, world!")

  val entityComponentManager = EntityComponentManager()

  val entityId = entity(entityComponentManager) {
    + HealthAttribute(100)
    + NumberAttribute(55.5f)
    + NumberAttribute(22)
  }

  println("Entity: ${entityId}")
  val healthAttributes = entityComponentManager.getComponents<HealthAttribute<Int>>(entityId).map({ it.value })
  println("HealthAttribute<Int>: ${healthAttributes}")
  val healthAttribute = entityComponentManager.getComponent<HealthAttribute<*>>(entityId).value
  println("Single HealthAttribute<*>: ${healthAttribute}")

  val numberAttributes = entityComponentManager.getComponents<NumberAttribute<Int>>(entityId).map({ it.value })
  println("NumberAttribute<Int>: ${numberAttributes}")

  val numberAttributesFloat = entityComponentManager.getComponents<NumberAttribute<Float>>(entityId).map({ it.value })
  println("NumberAttribute<Float>: ${numberAttributesFloat}")

  val numberAttributesWild = entityComponentManager.getComponents<NumberAttribute<*>>(entityId).map({ it.value })
  println("NumberAttribute<*>: ${numberAttributesWild}")

  val allComponents = entityComponentManager.getAllComponents(entityId)
  println("All components: ${allComponents}")

}
