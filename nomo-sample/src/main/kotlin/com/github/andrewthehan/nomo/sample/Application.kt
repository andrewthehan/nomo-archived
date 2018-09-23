package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.boot.ecs.components.attributes.*
import com.github.andrewthehan.nomo.boot.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.boot.ecs.events.*
import com.github.andrewthehan.nomo.core.ecs.interfaces.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.sdk.ecs.annotations.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.*
import com.github.andrewthehan.nomo.sdk.ecs.managers.*
import com.github.andrewthehan.nomo.sdk.ecs.tasks.*
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*
import com.github.andrewthehan.nomo.sdk.ecs.systems.*
import com.github.andrewthehan.nomo.sdk.ecs.util.*
import com.github.andrewthehan.nomo.util.collections.*
import com.github.andrewthehan.nomo.util.*

import kotlin.reflect.KClass

class EcsEngine() : Engine {
  override val managers = TypedSet<Manager>()
  override val tasks = TypedSet<Task>()

  override fun update(delta: Float) {
    val taskClasses = tasks.map { it::class }
    val orderedTaskClasses = getOrder(taskClasses)
    orderedTaskClasses
      .map { tasks.get(it)!! }
      .forEach { it.update(delta) }
  }
}

fun main(args: Array<String>) {
  println("Hello, world!")

  val engine = EcsEngine().apply {
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

  val systemsManager = engine.managers.get<SystemsManager>()!!
  systemsManager.systems.apply {
    add(UpdateSystem())
  }

  (0..1000).forEach {
    createEntity(engine.managers.get<EntityComponentManager>()!!, "#${it}", 50, .1f)
  }

  (0..1000).forEach {
    createEntity2(engine.managers.get<EntityComponentManager>()!!, "##${it}", 1000)
  }

  // Simulate game loop
  var i = 0
  (0..100).forEach {
  // while(engine.managers.get<EntityComponentManager>()!!.getAllEntities().any()) {
    println("******** LOOP ${++i} ********")
    engine.update(10f)
  }
}

val continuousDamageBehavior = ContinuousDamageBehavior()
val removeOnDeathBehavior = RemoveOnDeathBehavior()

fun createEntity(entityComponentManager: EntityComponentManager, entity: Entity, health: Int, armor: Float) {
  val components = arrayOf(
    HealthAttribute(health),
    ArmorBehavior(armor),
    continuousDamageBehavior,
    DamageableBehavior(),
    removeOnDeathBehavior
    // EventLogBehavior()
  )
  entityComponentManager.add(entity, components)
}

fun createEntity2(entityComponentManager: EntityComponentManager, entity: Entity, health: Int) {
  val components = arrayOf(
    HealthAttribute(health),
    DamageableBehavior(),
    removeOnDeathBehavior
    // EventLogBehavior()
  )
  entityComponentManager.add(entity, components)
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
