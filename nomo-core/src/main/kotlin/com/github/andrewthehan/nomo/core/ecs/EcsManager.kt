package com.github.andrewthehan.nomo.core.ecs

import com.github.andrewthehan.nomo.core.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.core.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.EcsObject
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.util.collections.BiMultiMap
import com.github.andrewthehan.nomo.util.collections.MultiMap

class EcsManager {
  val idToObjectMap = MultiMap<EcsId, EcsObject>()
  val entitiesToComponentsMap = BiMultiMap<Entity, Component>()

  fun register(ecsObject: EcsObject) {
    if (idToObjectMap.containsKey(ecsObject.id)) {
      return
    }

    idToObjectMap.put(ecsObject.id, ecsObject)
  }

  // inline fun <EntityType: Entity, reified ExclusiveComponentType> addComponent(entity: EntityType, component: ExclusiveComponentType)
  //   where ExclusiveComponentType : Component, ExclusiveComponentType : Exclusive {
  //   if (component is Exclusive) {
  //     getComponent<ExclusiveComponentType>(entity) ?: throw Exception("Exclusive!")
  //   }
  //   addComponent(entity, component as Component)
  // }

  // inline fun <reified EntityType: Entity, reified PendantComponentType> addComponent(entity: EntityType, component: PendantComponentType)
  //   where PendantComponentType : Component, PendantComponentType : Pendant {
  //   if (component is Pendant) {
  //     getEntity<EntityType, PendantComponentType>(component) ?: throw Exception("Pendant!")
  //   }
  //   addComponent(entity, component as Component)
  // }

  fun addComponent(entity: Entity, component: Component) {
    entitiesToComponentsMap.put(entity, component)
  }

  fun removeComponent(entity: Entity, component: Component) = entitiesToComponentsMap.remove(entity, component)

  inline fun <reified ExclusiveComponentType> getComponent(entity: Entity)
    where ExclusiveComponentType : Component, ExclusiveComponentType : Exclusive
    = entitiesToComponentsMap[entity].find { it is ExclusiveComponentType }

  inline fun <reified ComponentType: Component> getComponents(entity: Entity)
    = entitiesToComponentsMap[entity].filter { it is ComponentType }

  fun getComponents(entity: Entity) = entitiesToComponentsMap[entity]
  
  inline fun <reified EntityType: Entity, PendantComponentType> getEntity(component: PendantComponentType)
    where PendantComponentType : Component, PendantComponentType : Pendant
    = entitiesToComponentsMap.reverse[component].find { it is EntityType }

  inline fun <reified EntityType: Entity> getEntities(component: Component)
    = entitiesToComponentsMap.reverse[component].filter { it is EntityType }

  fun getEntities(component: Component) = entitiesToComponentsMap.reverse[component]
}
