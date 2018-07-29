package com.github.andrewthehan.nomo.core.ecs.types

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.core.ecs.EcsEngine

interface Task: Updatable<Float> {
  val ecsEngine: EcsEngine
}