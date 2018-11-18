package com.github.andrewthehan.nomo.boot.io.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.math.vectors.Vector2i

data class MousePointerEvent(val position: Vector2i, val source: EcsId) : Event