package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.util.getEventListeners
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.util.filterAs
import com.github.andrewthehan.nomo.util.getAnnotation

import kotlin.reflect.full.isSubclassOf

class EventManager(override val ecsEngine: EcsEngine) : Manager {
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }
  private val dependencyInjectionManager by lazy { ecsEngine.managers.get<DependencyInjectionManager>()!! }

  private fun dispatchEvent(behaviors: Set<Behavior>, event: Event) {
    behaviors.forEach { behavior -> 
      dependencyInjectionManager.injectManagers(behavior)
      getEventListeners(behavior::class)
        .filter { event::class.isSubclassOf(it.getAnnotation<EventListener>().value) }
        .forEach { it.call(behavior, event) }
    }
  }

  fun dispatchEvent(event: Event) {
    val behaviors = entityComponentManager
      .getAllComponents()
      .filterAs<Behavior>()
    dispatchEvent(behaviors, event)
  }

  fun dispatchEvent(entity: Entity, event: Event) {
    val behaviors = entityComponentManager
      .getAllComponents(entity)
      .filterAs<Behavior>()
    dispatchEvent(behaviors, event)
  }
}
