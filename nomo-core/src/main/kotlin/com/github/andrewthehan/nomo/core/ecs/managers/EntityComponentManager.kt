package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.core.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.core.ecs.exceptions.ExclusiveException
import com.github.andrewthehan.nomo.core.ecs.exceptions.PendantException
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.util.hasAnnotation
import com.github.andrewthehan.nomo.util.collections.BiMultiMap

class EntityComponentManager(val ecsManager: EcsManager) {
  val entitiesToComponentsMap = BiMultiMap<Entity, Component>()

  inline fun <reified ActualComponent: Component> addEntityComponent(entity: Entity, component: ActualComponent) {
    val componentTypeClass = component::class
    if (componentTypeClass.hasAnnotation<Exclusive>()) {
      if (entitiesToComponentsMap[entity].any { it is ActualComponent }) {
        throw ExclusiveException()
      }
    }
    if (componentTypeClass.hasAnnotation<Pendant>()) {
      if (!entitiesToComponentsMap.reverse[component].isEmpty()) {
        throw PendantException()
      } 
    }

    entitiesToComponentsMap.put(entity, component)
  }

  fun removeEntityComponent(entity: Entity, component: Component) = entitiesToComponentsMap.remove(entity, component)

  fun removeEntity(entity: Entity) = entitiesToComponentsMap.removeKey(entity)

  fun removeComponent(component: Component) = entitiesToComponentsMap.removeValue(component)

  fun getAllEntities() = entitiesToComponentsMap.keys
  
  fun <PendantComponent: @Pendant Component> getEntity(component: PendantComponent) = entitiesToComponentsMap.reverse[component].firstOrNull()

  fun getEntities(component: Component) = entitiesToComponentsMap.reverse[component]

  fun getAllComponents() = entitiesToComponentsMap.values

  fun getAllComponents(entity: Entity) = entitiesToComponentsMap[entity]

  inline fun <reified ExclusiveComponent: @Exclusive Component> getComponent(entity: Entity)
    = entitiesToComponentsMap[entity].find { it is ExclusiveComponent } as ExclusiveComponent

  inline fun <reified ActualComponent: Component> getComponents(entity: Entity)
    = entitiesToComponentsMap[entity].filter { it is ActualComponent }.map { it as ActualComponent }.toMutableSet()
}