package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.util.EventDispatchInfo
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

class EventManager(override val ecsEngine: EcsEngine) : Manager {
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }
  
  val events = mutableSetOf<EventDispatchInfo>()

  fun dispatchEvent(event: Event) = events.add(EventDispatchInfo(event, null))

  fun dispatchEvent(event: Event, behavior: Behavior) = events.add(EventDispatchInfo(event, arrayOf(behavior)))

  fun dispatchEvent(event: Event, behaviors: Array<Behavior>) = events.add(EventDispatchInfo(event, behaviors))

  fun dispatchEvent(event: Event, entity: Entity)
    = events.add(EventDispatchInfo(event, entityComponentManager.getComponents<Behavior>(entity).toTypedArray()))

  fun dispatchEvent(event: Event, entities: Array<Entity>) {
    val behaviors = entities
      .map { entityComponentManager.getComponents<Behavior>(it) }
      .flatten()
      .distinct()
      .toTypedArray()
    events.add(EventDispatchInfo(event, behaviors))
  }
}
