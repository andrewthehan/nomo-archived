package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Event

data class EventDispatchInfo(val event: Event, val behaviors: Array<Behavior>?)