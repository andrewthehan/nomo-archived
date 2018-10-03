package com.github.andrewthehan.nomo.sdk.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.sdk.ecs.annotations.After
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Afters
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Consumable
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.tasks.InjectionTask
import com.github.andrewthehan.nomo.sdk.ecs.util.EventDispatchInfo
import com.github.andrewthehan.nomo.sdk.ecs.util.getEventListeners
import com.github.andrewthehan.nomo.sdk.ecs.util.getFunctionOrder
import com.github.andrewthehan.nomo.util.filterAs
import com.github.andrewthehan.nomo.util.getAnnotation

import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.KFunction

@Afters(
  After(include = [ SystemsUpdateTask::class ])
)
class EventPropagationTask(override val engine: Engine) : Task {
  private val eventManager by lazy { engine.managers.get<EventManager>()!! }
  private val entityComponentManager by lazy { engine.managers.get<EntityComponentManager>()!! }
  private val injectionTask by lazy { engine.tasks.get<InjectionTask>()!! }

  override fun update(delta: Float) {
    val allBehaviors = entityComponentManager
      .getAllComponents()
      .filterAs<Behavior>()
    val allBehaviorClasses = allBehaviors
      .map { it::class }
      .distinct();

    val eventListenerOrder = getFunctionOrder(allBehaviorClasses, { it.getEventListeners() })

    val events = eventManager.events.toSet()
    eventManager.events.clear()

    allBehaviors.parallelStream().forEach { injectionTask.injectManagers(it) }

    val behaviorClassToBehaviorsMap = allBehaviors.groupBy { it::class }
    val eventClassToEventsMap = events.groupBy { it.event::class }

    eventListenerOrder.forEach { eventListener ->
      val eventListenerParameters = eventListener.parameters
      // expect 2 parameters, one internally for the instance and one for the event
      if (eventListenerParameters.size != 2) {
        throw IllegalStateException("Event listeners should only have 1 event parameter ${eventListenerParameters}")
      }
      val eventType = eventListener.parameters[1].type.jvmErasure
      val relevantEvents = eventClassToEventsMap
        .filter { it.key.isSubclassOf(eventType) }
        .flatMap { it.value }
      if (relevantEvents.none()) {
        return@forEach
      }

      val relevantBehaviors = allBehaviorClasses
        .filter { it.functions.contains(eventListener) }
        .flatMap { behaviorClassToBehaviorsMap.get(it)!! }

      relevantEvents.forEach outer@{ eventInfo ->
        val event = eventInfo.event
        if (event is Consumable && (event as Consumable).isConsumed) {
          return@outer
        }
        val behaviors = eventInfo.behaviors?.filter { it::class.functions.contains(eventListener) } ?: relevantBehaviors
        behaviors.forEach inner@{
          if (!entityComponentManager.containsComponent(it)) {
            return@inner
          }
          eventListener.call(it, event)
        }
      }
    }
  }
}