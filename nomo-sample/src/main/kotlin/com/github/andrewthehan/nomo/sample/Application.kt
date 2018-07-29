package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.annotations.*
import com.github.andrewthehan.nomo.core.ecs.managers.*
import com.github.andrewthehan.nomo.core.ecs.tasks.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.sdk.ecs.entity.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*
import com.github.andrewthehan.nomo.sdk.ecs.systems.*

fun main(args: Array<String>) {
  println("Hello, world!")

  val ecsEngine = EcsEngine().apply {
    val ecs = this
    managers.apply {
      add(DependencyInjectionManager(ecs))
      add(EntityComponentManager(ecs))
      add(EventManager(ecs))
      add(SystemsManager(ecs))
    }
    tasks.apply {
      add(SystemsUpdateTask(ecs))
      add(DependencyValidatorTask(ecs))
    }
  }

  val systemsManager = ecsEngine.managers.get<SystemsManager>()!!
  systemsManager.systems.apply {
    add(UpdateSystem())
  }

  createEntity(ecsEngine)

  // Simulate game loop
  for (i in 0..12) {
    ecsEngine.update(10f)
  }
}

fun createEntity(ecsEngine: EcsEngine) {
  val entityComponentManager = ecsEngine.managers.get<EntityComponentManager>()!!
  entity(entityComponentManager) {
    + HealthAttribute(100)
    + ContinuousDamageBehavior()
    + DamageableBehavior()
    + DeathBehavior()
    + EventLogBehavior()
  }
}

class EventLogBehavior : AbstractBehavior() {
  @EventListener(Event::class)
  fun log(event: Event) {
    println("Event received: ${event::class.simpleName}")
  }
}

class DamageEvent<NumberType: Number>(val entity: Entity, val amount: NumberType) : Event

class DeathEvent(val entity: Entity) : Event

class ContinuousDamageBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(UpdateEvent::class)
  fun drainHealth(event: UpdateEvent) {
    entityComponentManager
      .getEntities(this)
      .forEach { eventManager.dispatchEvent(DamageEvent(it, event.delta)) }
  }
}

@Dependent(HealthAttribute::class)
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
