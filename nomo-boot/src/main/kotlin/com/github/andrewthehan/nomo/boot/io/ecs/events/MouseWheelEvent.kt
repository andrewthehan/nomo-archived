package com.github.andrewthehan.nomo.boot.io.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Event

class MouseWheelEvent(val delta: Int, val source: EcsId) : Event