package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.core.ecs.annotations.*
import com.github.andrewthehan.nomo.core.ecs.interfaces.*
import com.github.andrewthehan.nomo.core.ecs.managers.*
import com.github.andrewthehan.nomo.core.ecs.tasks.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.core.ecs.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*
import com.github.andrewthehan.nomo.sdk.ecs.systems.*
import com.github.andrewthehan.nomo.util.*

fun main(args: Array<String>) {
  println("Hello, world!")

  val ecsEngine = EcsEngine().apply {
    val ecs = this
    managers.apply {
      add(EntityComponentManager(ecs))
      add(EventManager(ecs))
      add(SystemsManager(ecs))
    }
    tasks.apply {
      add(InjectionTask(ecs))
      add(SystemsUpdateTask(ecs))
      add(DependencyValidatorTask(ecs))
      add(EventPropagationTask(ecs))
    }
  }

  val systemsManager = ecsEngine.managers.get<SystemsManager>()!!
  systemsManager.systems.apply {
    add(UpdateSystem())
  }

  (0..1000).forEach {
    createEntity(ecsEngine.managers.get<EntityComponentManager>()!!, "#${it}", 1000, .1f)
  }

  // Simulate game loop
  var i = 0
  // (0..10).forEach {
  while(ecsEngine.managers.get<EntityComponentManager>()!!.getAllEntities().any()) {
    println("******** LOOP ${++i} ********")
    ecsEngine.update(10f)
  }
}

val continuousDamageBehavior = ContinuousDamageBehavior()
val deathBehavior = DeathBehavior()

fun createEntity(entityComponentManager: EntityComponentManager, entity: Entity, health: Int, armor: Float) {
  entity.apply {
    entityComponentManager.add(this, HealthAttribute(health))
    entityComponentManager.add(this, ArmorBehavior(armor))
    entityComponentManager.add(this, continuousDamageBehavior)
    entityComponentManager.add(this, DamageableBehavior())
    entityComponentManager.add(this, deathBehavior)
    // entityComponentManager.add(this, EventLogBehavior())
  }
}

@Befores(
  Before(include = [ Component::class ], exclude = [ EventLogBehavior::class ])
)
class EventLogBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(Event::class)
  fun log(event: Event) {
    entityComponentManager
      .getEntities(this)
      .forEach { println("Entity (${it}) receives ${event}") }
  }
}

data class DamageEvent<NumberType: Number>(var amount: NumberType) : Event

data class DeathEvent(val entity: Entity) : Event

@Dependent(DamageableBehavior::class, HealthAttribute::class)
class ContinuousDamageBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(UpdateEvent::class)
  fun drainHealth(event: UpdateEvent) {
    val entities = entityComponentManager.getEntities(this)
    entities.forEach { eventManager.dispatchEvent(DamageEvent(event.delta), it) }
    // eventManager.dispatchEvent(DamageEvent(event.delta), entities)
  }
}

@Dependent(DamageableBehavior::class)
class ArmorBehavior(val reduction: Float) : AbstractBehavior() {
  @EventListener(DamageEvent::class)
  @Befores(
    Before(include = [ DamageableBehavior::class ])
  )
  fun applyArmor(event: DamageEvent<Int>) {
    val before = event.amount
    val after = (event.amount * reduction).toInt()
    // println("Armor reduces damage: ${before} (${reduction}) -> ${after}")
    event.amount = after
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
    entityComponentManager
      .getEntities(this)
      .forEach {
        val healthAttribute = entityComponentManager.getComponent<HealthAttribute<Int>>(it)
        val before = healthAttribute.value
        healthAttribute.damage(event.amount)
        val after = healthAttribute.value
        // println("${it} Health damaged: ${before} - ${event.amount} = ${after}")
        if (healthAttribute.isDead()) {
          eventManager.dispatchEvent(DeathEvent(it), it)
        }
      }
  }
}

class DeathBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(DeathEvent::class)
  fun onDeath(event: DeathEvent) {
    val entity = entityComponentManager
      .getEntities(this)
      .find { it == event.entity }!!

    // println("Death.")
    entityComponentManager.remove(entity)
  }
}
