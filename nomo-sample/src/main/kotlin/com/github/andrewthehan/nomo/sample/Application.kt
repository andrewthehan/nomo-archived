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

  createEntity(ecsEngine)

  // Simulate game loop
  (0..15).forEach {
    println("******** LOOP ********")
    ecsEngine.update(10f)
  }
}

fun createEntity(ecsEngine: EcsEngine) {
  val entityComponentManager = ecsEngine.managers.get<EntityComponentManager>()!!
  entity(entityComponentManager) {
    + HealthAttribute(100)
    + ContinuousDamageBehavior()
    + DamageableBehavior()
    + ArmorBehavior(.8f)
    + DeathBehavior()
    + EventLogBehavior()
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
    if (!entityComponentManager.containsComponent(this)) {
      return
    }

    val entity = entityComponentManager.getEntity(this)!!
    println("Entity (${entity}) receives ${event}")
  }
}

data class DamageEvent<NumberType: Number>(val entity: Entity, var amount: NumberType) : Event

data class DeathEvent(val entity: Entity) : Event

@Dependent(DamageableBehavior::class, HealthAttribute::class)
class ContinuousDamageBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(UpdateEvent::class)
  fun drainHealth(event: UpdateEvent) {
    if (!entityComponentManager.containsComponent(this)) {
      return
    }

    val entity = entityComponentManager.getEntity(this)!!
    entityComponentManager
      .getEntities(this)
      .forEach { eventManager.dispatchEvent(DamageEvent(it, event.delta), entity) }
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
    println("Armor reduces damage: ${before} (${reduction}) -> ${after}")
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
    entityComponentManager.getEntities(this)
      .filter { it == event.entity }
      .map { entityComponentManager.getComponent<HealthAttribute<Int>>(it) }
      .forEach {
        val before = it.value
        it.damage(event.amount)
        val after = it.value
        println("Health damaged: ${before} - ${event.amount} = ${after}")
        if (it.isDead()) {
          val entity = entityComponentManager.getEntity(it)!!
          eventManager.dispatchEvent(DeathEvent(entity), entity)
        }
      }
  }
}

class DeathBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener(DeathEvent::class)
  fun onDeath(event: DeathEvent) {
    if (!entityComponentManager.containsComponent(this)) {
      return
    }
    
    val entity = entityComponentManager.getEntity(this)!!
    if (event.entity == entity) {
      println("Death.")
      entityComponentManager.remove(entity)
    }
  }
}
