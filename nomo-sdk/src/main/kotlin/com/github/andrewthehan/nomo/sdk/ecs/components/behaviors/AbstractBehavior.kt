package com.github.andrewthehan.nomo.sdk.ecs.components.behaviors

import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.util.randomId

abstract class AbstractBehavior(override val id: String = randomId()) : Behavior