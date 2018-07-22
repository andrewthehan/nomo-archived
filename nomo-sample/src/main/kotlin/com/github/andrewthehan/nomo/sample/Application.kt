package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.annotations.*
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

  val systemManager = ecsEngine.getManager<SystemManager>()!!
  systemManager.addSystem(UpdateSystem())

  createEntity(ecsEngine)

  // Simulate game loop
  for (i in 0..12) {
    systemManager.update(10f)
  }
}

fun createEntity(ecsEngine: EcsEngine) {
  val entityComponentManager = ecsEngine.getManager<EntityComponentManager>()!!
  entity(entityComponentManager) {
    + HealthAttribute(100)
    + HealthDrainBehavior()
    + DamageableBehavior()
    + DeathBehavior()
  }
}

class DamageEvent<NumberType: Number>(val entity: Entity, val amount: NumberType) : Event

class DeathEvent(val entity: Entity) : Event

class HealthDrainBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(UpdateEvent::class)
  fun drainHealth(event: UpdateEvent) {
    println("Draining health...")
    entityComponentManager
      .getEntities(this)
      .forEach { eventManager.dispatchEvent(DamageEvent(it, event.delta)) }
  }
}

class DamageableBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(DamageEvent::class)
  fun onDamage(event: DamageEvent<Int>) {
    entityComponentManager.getEntities(this)
      .filter { it == event.entity }
      .map { entityComponentManager.getComponent<HealthAttribute<Int>>(it) }
      .forEach {
        println("Health: ${it.value} (- ${event.amount})")
        it.damage(event.amount)
        if (it.isDead()) {
          val entity = entityComponentManager.getEntity(it)!!
          eventManager.dispatchEvent(DeathEvent(entity))
          entityComponentManager.removeEntityComponent(entity, this)
          println("Removing DamageableBehavior...")
        }
      }
  }
}

class DeathBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(DeathEvent::class)
  fun onDeath(event: DeathEvent) {
    if (event.entity == entityComponentManager.getEntity(this)!!) {
      println("Death.")
    }
  }
}
