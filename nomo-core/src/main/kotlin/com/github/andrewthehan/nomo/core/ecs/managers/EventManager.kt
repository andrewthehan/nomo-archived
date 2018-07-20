package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.core.ecs.types.Behavior

import kotlin.collections.flatten
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf

class EventManager(val ecsManager: EcsManager) {

  inline fun <reified ActualEvent: Event> dispatchEvent(event: ActualEvent) {
    val entityComponentManager = ecsManager.entityComponentManager
    val metadataManager = ecsManager.metadataManager
    
    val behaviors = entityComponentManager.getAllComponents().filter { it is Behavior }.map { it as Behavior }
    behaviors
      .map { behavior -> metadataManager
        .getEventListeners(behavior)
        .filter { it.findAnnotation<EventListener>()?.let { it.value.isSubclassOf(ActualEvent::class) } ?: false }
        .forEach { it.call(behavior, event) }
      }
  }
}
