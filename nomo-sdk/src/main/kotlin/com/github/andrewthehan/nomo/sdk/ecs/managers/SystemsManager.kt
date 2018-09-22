package com.github.andrewthehan.nomo.sdk.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.util.collections.TypedSet

class SystemsManager(override val ecsEngine: EcsEngine) : Manager {
  val systems = TypedSet<System>()
}