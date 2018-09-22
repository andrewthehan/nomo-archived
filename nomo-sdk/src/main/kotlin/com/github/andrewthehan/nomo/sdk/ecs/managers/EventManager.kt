package com.github.andrewthehan.nomo.sdk.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.util.EventDispatchInfo
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

class EventManager(override val ecsEngine: EcsEngine) : Manager {
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }
  
  val events = mutableSetOf<EventDispatchInfo>()

  fun dispatchEvent(event: Event) = events.add(EventDispatchInfo(event, null))

  fun dispatchEvent(event: Event, behavior: Behavior) = events.add(EventDispatchInfo(event, setOf(behavior)))

  fun dispatchEvent(event: Event, behaviors: Collection<Behavior>) = events.add(EventDispatchInfo(event, behaviors))

  fun dispatchEvent(event: Event, entity: Entity)
    = events.add(EventDispatchInfo(event, entityComponentManager.getComponents<Behavior>(entity).toSet()))

  fun dispatchEvent(event: Event, entities: Collection<Entity>) {
    val behaviors = entities
      .flatMap { entityComponentManager.getComponents<Behavior>(it) }
      .distinct()
      .toSet()
    events.add(EventDispatchInfo(event, behaviors))
  }
}
