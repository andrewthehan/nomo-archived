package com.github.andrewthehan.nomo.boot.io.ecs.events

import com.github.andrewthehan.nomo.boot.io.MouseButton
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.math.vectors.MutableVector2i

abstract class MouseButtonEvent(val mouseButton: MouseButton, val position: MutableVector2i, val source: EcsId) : Event

class MouseButtonPressEvent(mouseButton: MouseButton, position: MutableVector2i, source: EcsId) : MouseButtonEvent(mouseButton, position, source)
class MouseButtonReleaseEvent(mouseButton: MouseButton, position: MutableVector2i, source: EcsId) : MouseButtonEvent(mouseButton, position, source)
class MouseButtonHoldEvent(mouseButton: MouseButton, position: MutableVector2i, source: EcsId) : MouseButtonEvent(mouseButton, position, source)