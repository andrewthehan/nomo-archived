package com.github.andrewthehan.nomo.core.ecs

import com.github.andrewthehan.nomo.core.ecs.types.Manager

import kotlin.reflect.KClass

class EcsEngine {
  val managers = HashMap<KClass<out Manager>, Manager>()

  fun addManager(manager: Manager) = managers.put(manager::class, manager)

  inline fun <reified ActualManager : Manager> getManager() = managers.get(ActualManager::class) as? ActualManager

  @Suppress("Unchecked_cast")
  fun <ActualManager : Manager> getManager(managerClass: KClass<ActualManager>) = managers.get(managerClass) as? ActualManager

  inline fun <reified ActualManager : Manager> removeManager() = managers.remove(ActualManager::class)

  fun removeManager(manager: Manager) = managers.remove(manager::class)
}
