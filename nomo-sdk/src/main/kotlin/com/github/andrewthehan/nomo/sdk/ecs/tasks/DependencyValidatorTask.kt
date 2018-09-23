package com.github.andrewthehan.nomo.sdk.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.sdk.ecs.exceptions.MissingDependencyException
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.util.getMissingDependencies
import com.github.andrewthehan.nomo.sdk.ecs.util.hasDependencies

class DependencyValidatorTask(override val engine: Engine) : Task {
  private val entityComponentManager by lazy { engine.managers.get<EntityComponentManager>()!! }

  private fun checkDependencies(entity: Entity) {
    val componentClasses = entityComponentManager.getAllComponents(entity).map { it::class }
    
    if (!hasDependencies(componentClasses)) {
      throw MissingDependencyException(entity, getMissingDependencies(componentClasses))
    }
  }

  override fun update(delta: Float) {
    entityComponentManager
      .getAllEntities()
      .parallelStream()
      .forEach { checkDependencies(it) }
  }
}
