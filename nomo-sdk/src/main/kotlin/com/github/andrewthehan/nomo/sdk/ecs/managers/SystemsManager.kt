package com.github.andrewthehan.nomo.sdk.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.util.collections.TypedSet

class SystemsManager(override val engine: Engine) : Manager {
  val systems = TypedSet<System>()
}