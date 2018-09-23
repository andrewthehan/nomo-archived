package com.github.andrewthehan.nomo.core.ecs.types

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable

interface Task: Updatable<Float> {
  val engine: Engine
}