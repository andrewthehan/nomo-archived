package com.github.andrewthehan.nomo.sdk.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.sdk.ecs.exceptions.ExclusiveException
import com.github.andrewthehan.nomo.sdk.ecs.exceptions.PendantException
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.util.collections.BiMultiMap
import com.github.andrewthehan.nomo.util.filterAs
import com.github.andrewthehan.nomo.util.singleAs
import com.github.andrewthehan.nomo.util.hasAnnotation

import kotlin.reflect.full.isSubclassOf

class EntityComponentManager(override val engine: Engine) : Manager {
  val entitiesToComponentsMap = BiMultiMap<Entity, Component>()

  fun add(entity: Entity, component: Component) {
    if (component is Exclusive) {
      val componentClass = component::class
      val componentTypeAlreadyBound = entitiesToComponentsMap[entity]
        .filter { it is Exclusive }
        .map { it::class }
        .any { it.isSubclassOf(componentClass) || componentClass.isSubclassOf(it) }
      if (componentTypeAlreadyBound) {
        throw ExclusiveException(entity, component::class)
      }
    }
    if (component is Pendant) {
      if (entitiesToComponentsMap.reverse[component].any()) {
        throw PendantException(component, entitiesToComponentsMap.reverse[component])
      }
    }

    entitiesToComponentsMap.put(entity, component)
  }

  fun add(entity: Entity, components: Array<Component>) = components.forEach { add(entity, it) }

  fun add(entity: Entity, components: Iterable<Component>) = components.forEach { add(entity, it) }

  fun add(entities: Array<Entity>, component: Component) = entities.forEach { add(it, component) }

  fun add(entities: Iterable<Entity>, component: Component) = entities.forEach { add(it, component) }

  fun remove(entity: Entity, component: Component) = entitiesToComponentsMap.remove(entity, component)

  fun remove(entity: Entity) = entitiesToComponentsMap.removeKey(entity)

  fun remove(component: Component) = entitiesToComponentsMap.removeValue(component)

  fun containsEntity(entity: Entity) = entitiesToComponentsMap.containsKey(entity)

  fun containsComponent(component: Component) = entitiesToComponentsMap.containsValue(component)

  fun getAllEntities() = entitiesToComponentsMap.keys.toSet()
  
  fun <PendantComponent> getEntity(component: PendantComponent) where PendantComponent : Component, PendantComponent : Pendant =
    entitiesToComponentsMap.reverse[component].singleOrNull()

  fun getEntities(component: Component) = entitiesToComponentsMap.reverse[component].toSet()

  fun getAllComponents() = entitiesToComponentsMap.values.toSet()

  fun getAllComponents(entity: Entity) = entitiesToComponentsMap[entity].toSet()

  inline fun <reified ExclusiveComponent> getComponent(entity: Entity) where ExclusiveComponent : Component, ExclusiveComponent : Exclusive
    = entitiesToComponentsMap[entity].singleAs<ExclusiveComponent>()

  inline fun <reified ActualComponent: Component> getComponents(entity: Entity)
    = entitiesToComponentsMap[entity].filterAs<ActualComponent>()

  inline fun <reified ActualComponent: Component> getComponents()
    = entitiesToComponentsMap.values.filterAs<ActualComponent>()
}