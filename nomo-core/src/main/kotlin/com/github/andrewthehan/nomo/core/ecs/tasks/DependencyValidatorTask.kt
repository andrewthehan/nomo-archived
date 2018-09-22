package com.github.andrewthehan.nomo.core.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.core.ecs.exceptions.MissingDependencyException
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.core.ecs.util.getDependencies
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

class DependencyValidatorTask(override val ecsEngine: EcsEngine) : Task {
  private val entityComponentManager by lazy { ecsEngine.managers.get<EntityComponentManager>()!! }

  private fun checkDependencies(entity: Entity) {
    val componentClasses = entityComponentManager.getAllComponents(entity).map { it::class }
    val isMissingDependencies = componentClasses
      .map { it.getDependencies() }
      .filter { it.any() }
      .any {
        val missingDependencies = it.subtract(componentClasses)
        missingDependencies.any()
      }
    
    if (isMissingDependencies) {
      val missingDependencies = componentClasses.flatMap {
        it.getDependencies().subtract(componentClasses)
      }
      throw MissingDependencyException(entity, missingDependencies)
    }
  }

  override fun update(delta: Float) {
    entityComponentManager
      .getAllEntities()
      .parallelStream()
      .forEach { checkDependencies(it) }
  }
}
