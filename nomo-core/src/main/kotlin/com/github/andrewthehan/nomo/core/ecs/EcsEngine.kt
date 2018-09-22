package com.github.andrewthehan.nomo.core.ecs

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.util.collections.TypedSet

class EcsEngine : Updatable<Float> {
  val managers = TypedSet<Manager>()
  val tasks = TypedSet<Task>()

  override fun update(delta: Float) = tasks.forEach { 
    it.update(delta)
  }
}
