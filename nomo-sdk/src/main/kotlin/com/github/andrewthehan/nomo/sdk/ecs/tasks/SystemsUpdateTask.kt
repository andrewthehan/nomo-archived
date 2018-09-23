package com.github.andrewthehan.nomo.sdk.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.sdk.ecs.managers.SystemsManager

class SystemsUpdateTask(override val engine: Engine) : Task {
  private val systemsManager by lazy { engine.managers.get<SystemsManager>()!! }

  override fun update(delta: Float) {
    systemsManager.systems.forEach {
      it.update(delta)
    }
  }
}
