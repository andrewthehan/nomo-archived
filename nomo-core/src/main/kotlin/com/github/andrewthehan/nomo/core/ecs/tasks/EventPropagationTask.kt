package com.github.andrewthehan.nomo.core.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.managers.EventManager
import com.github.andrewthehan.nomo.core.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.core.ecs.tasks.InjectionTask
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.core.ecs.util.EventDispatchInfo
import com.github.andrewthehan.nomo.core.ecs.util.getEventListeners
import com.github.andrewthehan.nomo.core.ecs.util.getEventListenerOrder
import com.github.andrewthehan.nomo.util.filterAs
import com.github.andrewthehan.nomo.util.getAnnotation
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.KFunction

class EventPropagationTask(override val ecsEngine: EcsEngine) : Task {
  private val eventManager by lazy { ecsEngine.managers.get<EventManager>()!! }
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }
  private val injectionTask by lazy { ecsEngine.tasks.get<InjectionTask>()!! }

  private fun <EventListenerFunction : @EventListener KFunction<*>> propagateEvent(behavior: Behavior, eventListener: EventListenerFunction, event: Event) {
    injectionTask.injectManagers(behavior)
    eventListener.call(behavior, event)
  }

  override fun update(delta: Float) {
    val allBehaviors = entityComponentManager.getAllComponents().filterAs<Behavior>();
    val eventListenerOrder = getEventListenerOrder(allBehaviors)

    val events = eventManager.events.toSet()
    eventManager.events.clear()

    eventListenerOrder.forEach { (behavior, eventListener, eventType) ->
      if (!entityComponentManager.containsComponent(behavior)) {
        return@forEach
      }

      events
        .filter { it.event::class.isSubclassOf(eventType) }
        .filter { 
          val targetBehaviors: Iterable<Behavior> = it.behaviors?.asIterable() ?: allBehaviors
          targetBehaviors.contains(behavior)
        }
        .forEach { propagateEvent(behavior, eventListener, it.event) }
    }
  }
}