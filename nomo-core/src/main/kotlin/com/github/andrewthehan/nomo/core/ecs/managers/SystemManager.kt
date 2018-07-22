package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

class SystemManager(override val ecsEngine: EcsEngine) : Manager, Updatable<Float> {
  private val systems = HashMap<EcsId, System>()

  fun addSystem(system: System) = systems.put(system.id, system)

  fun removeSystem(id: EcsId) = systems.remove(id)

  fun removeSystem(system: System) = systems.remove(system.id, system)

  override fun update(delta: Float) {
    val dependencyInjectionManager = ecsEngine.getManager<DependencyInjectionManager>()!!
    systems.values.forEach {
      dependencyInjectionManager.injectManagers(it)
      it.update(delta)
    }
  }
}
