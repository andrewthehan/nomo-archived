package com.github.andrewthehan.nomo.sdk.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.tasks.InjectionTask
import com.github.andrewthehan.nomo.sdk.ecs.util.EventDispatchInfo
import com.github.andrewthehan.nomo.sdk.ecs.util.getEventListeners
import com.github.andrewthehan.nomo.sdk.ecs.util.getEventListenerOrder
import com.github.andrewthehan.nomo.util.filterAs
import com.github.andrewthehan.nomo.util.getAnnotation

import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.KFunction

class EventPropagationTask(override val ecsEngine: EcsEngine) : Task {
  private val eventManager by lazy { ecsEngine.managers.get<EventManager>()!! }
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }
  private val injectionTask by lazy { ecsEngine.tasks.get<InjectionTask>()!! }

  override fun update(delta: Float) {
    val allBehaviors = entityComponentManager
      .getAllComponents()
      .filterAs<Behavior>()
    val allBehaviorClasses = allBehaviors
      .map { it::class }
      .distinct();

    val eventListenerOrder = getEventListenerOrder(allBehaviorClasses)

    val events = eventManager.events.toSet()
    eventManager.events.clear()

    allBehaviors.parallelStream().forEach { injectionTask.injectManagers(it) }

    val behaviorClassToBehaviorsMap = allBehaviors.groupBy { it::class }
    val eventClassToEventsMap = events.groupBy { it.event::class }

    eventListenerOrder.forEach { eventListener ->
      val eventType = eventListener.getAnnotation<EventListener>().value
      val relevantEvents = eventClassToEventsMap
        .filter { it.key.isSubclassOf(eventType) }
        .flatMap { it.value }
      if (relevantEvents.none()) {
        return@forEach
      }

      val relevantBehaviors = allBehaviorClasses
        .filter { it.functions.contains(eventListener) }
        .flatMap { behaviorClassToBehaviorsMap.get(it)!! }

      relevantEvents.forEach { eventInfo ->
        val behaviors = eventInfo.behaviors?.filter { it::class.functions.contains(eventListener) } ?: relevantBehaviors
        behaviors.forEach inner@{
          if (!entityComponentManager.containsComponent(it)) {
            return@inner
          }
          eventListener.call(it, eventInfo.event)
        }
      }
    }
  }
}