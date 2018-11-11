package com.github.andrewthehan.nomo.boot.io.ecs.events

import com.github.andrewthehan.nomo.boot.io.MouseButton
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.math.vectors.Vector2i

abstract class MouseButtonEvent(val mouseButton: MouseButton, val position: Vector2i, val source: EcsId) : Event

class MouseButtonPressEvent(mouseButton: MouseButton, position: Vector2i, source: EcsId) : MouseButtonEvent(mouseButton, position, source)
class MouseButtonReleaseEvent(mouseButton: MouseButton, position: Vector2i, source: EcsId) : MouseButtonEvent(mouseButton, position, source)
class MouseButtonHoldEvent(mouseButton: MouseButton, position: Vector2i, source: EcsId) : MouseButtonEvent(mouseButton, position, source)