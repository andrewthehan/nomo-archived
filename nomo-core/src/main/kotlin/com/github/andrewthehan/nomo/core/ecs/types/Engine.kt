package com.github.andrewthehan.nomo.core.ecs.types

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.util.collections.TypedSet

interface Engine : Updatable<Float> {
  val managers: TypedSet<Manager>
  val tasks: TypedSet<Task>

  override fun update(delta: Float) {
    tasks.forEach { it.update(delta) }
  }
}
