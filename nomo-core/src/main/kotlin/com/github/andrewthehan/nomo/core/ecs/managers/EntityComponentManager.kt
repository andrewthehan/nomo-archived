package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.annotations.Exclusive
import com.github.andrewthehan.nomo.core.ecs.annotations.Pendant
import com.github.andrewthehan.nomo.core.ecs.exceptions.ExclusiveException
import com.github.andrewthehan.nomo.core.ecs.exceptions.PendantException
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.util.collections.BiMultiMap
import com.github.andrewthehan.nomo.util.filterAs
import com.github.andrewthehan.nomo.util.singleAs
import com.github.andrewthehan.nomo.util.hasAnnotation

class EntityComponentManager(override val ecsEngine: EcsEngine) : Manager {
  val entitiesToComponentsMap = BiMultiMap<Entity, Component>()

  inline fun <reified ActualComponent: Component> add(entity: Entity, component: ActualComponent) {
    val componentClass = component::class
    if (componentClass.hasAnnotation<Exclusive>()) {
      if (entitiesToComponentsMap[entity].any { it is ActualComponent }) {
        throw ExclusiveException()
      }
    }
    if (componentClass.hasAnnotation<Pendant>()) {
      if (!entitiesToComponentsMap.reverse[component].isEmpty()) {
        throw PendantException()
      }
    }

    entitiesToComponentsMap.put(entity, component)
  }

  fun remove(entity: Entity, component: Component) = entitiesToComponentsMap.remove(entity, component)

  fun remove(entity: Entity) = entitiesToComponentsMap.removeKey(entity)

  fun remove(component: Component) = entitiesToComponentsMap.removeValue(component)

  fun containsEntity(entity: Entity) = entitiesToComponentsMap.containsKey(entity)

  fun containsComponent(component: Component) = entitiesToComponentsMap.containsValue(component)

  fun getAllEntities() = entitiesToComponentsMap.keys
  
  fun <PendantComponent: @Pendant Component> getEntity(component: PendantComponent) = entitiesToComponentsMap.reverse[component].singleOrNull()

  fun getEntities(component: Component) = entitiesToComponentsMap.reverse[component]

  fun getAllComponents() = entitiesToComponentsMap.values

  fun getAllComponents(entity: Entity) = entitiesToComponentsMap[entity]

  inline fun <reified ExclusiveComponent: @Exclusive Component> getComponent(entity: Entity)
    = entitiesToComponentsMap[entity].singleAs<ExclusiveComponent>()

  inline fun <reified ActualComponent: Component> getComponents(entity: Entity)
    = entitiesToComponentsMap[entity].filterAs<ActualComponent>()
}