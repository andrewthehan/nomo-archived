package com.github.andrewthehan.nomo.core.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.core.ecs.exceptions.MissingDependencyException
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.core.ecs.util.getDependencies
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

class DependencyValidatorTask(override val ecsEngine: EcsEngine) : Task {
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }

  fun hasDependencies(entity: Entity) {
    val componentClasses = entityComponentManager.getAllComponents(entity).map { it::class }
    componentClasses.forEach {
      val missingDependencies = it.getDependencies().subtract(componentClasses)
      if (!missingDependencies.isEmpty()) {
        throw MissingDependencyException(it, missingDependencies)
      }
    }
  }

  override fun update(delta: Float) {
    entityComponentManager.getAllEntities().forEach { hasDependencies(it) }
  }
}
