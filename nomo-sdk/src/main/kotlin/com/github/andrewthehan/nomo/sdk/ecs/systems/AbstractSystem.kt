package com.github.andrewthehan.nomo.sdk.ecs.systems

import com.github.andrewthehan.nomo.core.ecs.managers.EcsManager
import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.util.randomId

abstract class AbstractSystem(id: String = randomId(), ecsManager: EcsManager) : System(id, ecsManager)