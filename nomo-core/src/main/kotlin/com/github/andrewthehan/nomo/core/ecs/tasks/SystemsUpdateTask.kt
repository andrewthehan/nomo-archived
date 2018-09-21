package com.github.andrewthehan.nomo.core.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.annotations.After
import com.github.andrewthehan.nomo.core.ecs.annotations.Afters
import com.github.andrewthehan.nomo.core.ecs.managers.SystemsManager
import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

@Afters(
  After(include = [ InjectionTask::class ])
)
class SystemsUpdateTask(override val ecsEngine: EcsEngine) : Task {
  private val systemsManager by lazy { ecsEngine.managers.get<SystemsManager>()!! }

  override fun update(delta: Float) {
    systemsManager.systems.forEach {
      it.update(delta)
    }
  }
}
