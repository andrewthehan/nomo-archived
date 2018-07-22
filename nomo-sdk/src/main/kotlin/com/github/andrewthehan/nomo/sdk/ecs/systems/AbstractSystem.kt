package com.github.andrewthehan.nomo.sdk.ecs.systems

import com.github.andrewthehan.nomo.core.ecs.types.System
import com.github.andrewthehan.nomo.util.randomId

abstract class AbstractSystem(override val id: String = randomId()) : System