package com.github.andrewthehan.nomo.boot.io.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.math.vectors.MutableVector2i

data class MousePointerEvent(val position: MutableVector2i, val source: EcsId) : Event