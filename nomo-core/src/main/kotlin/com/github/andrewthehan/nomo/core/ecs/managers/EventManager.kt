package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.util.getEventListeners
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.util.getAnnotation

import kotlin.reflect.full.isSubclassOf

class EventManager(override val ecsEngine: EcsEngine) : Manager {
  private val entityComponentManager by lazy { ecsEngine.getManager<EntityComponentManager>()!! }
  private val dependencyInjectionManager by lazy { ecsEngine.getManager<DependencyInjectionManager>()!! }

  fun dispatchEvent(event: Event) {
    val behaviors = entityComponentManager.getAllComponents().filter { it is Behavior }.map { it as Behavior }
    behaviors.forEach { dependencyInjectionManager.injectManagers(it) }
    behaviors
      .forEach { behavior -> getEventListeners(behavior::class)
        .filter { it.getAnnotation<EventListener>().value.isSubclassOf(event::class) }
        .forEach { it.call(behavior, event) }
      }
  }
}
