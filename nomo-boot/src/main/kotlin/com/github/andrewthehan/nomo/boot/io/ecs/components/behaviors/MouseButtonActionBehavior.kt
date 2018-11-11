package com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonHoldEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonReleaseEvent
import com.github.andrewthehan.nomo.boot.io.MouseButton
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.vectors.Vector2i

abstract class MouseButtonActionBehavior(val mouseButtonActionMap: Map<MouseButton, (Vector2i, Iterable<Entity>) -> Unit>) : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  protected fun tryAction(event: MouseButtonEvent) {
    if (!mouseButtonActionMap.containsKey(event.mouseButton)) {
      return
    }

    val entities = entityComponentManager[this]
    val action = mouseButtonActionMap.getValue(event.mouseButton)

    action(event.position, entities)
  }
}

class MouseButtonPressActionBehavior(mouseButtonActionMap: Map<MouseButton, (Vector2i, Iterable<Entity>) -> Unit>) : MouseButtonActionBehavior(mouseButtonActionMap) {
  @EventListener
  fun action(event: MouseButtonPressEvent) {
    tryAction(event)
  }
}

class MouseButtonReleaseActionBehavior(mouseButtonActionMap: Map<MouseButton, (Vector2i, Iterable<Entity>) -> Unit>) : MouseButtonActionBehavior(mouseButtonActionMap) {
  @EventListener
  fun action(event: MouseButtonReleaseEvent) {
    tryAction(event)
  }
}

class MouseButtonHoldActionBehavior(mouseButtonActionMap: Map<MouseButton, (Vector2i, Iterable<Entity>) -> Unit>) : MouseButtonActionBehavior(mouseButtonActionMap) {
  @EventListener
  fun action(event: MouseButtonHoldEvent) {
    tryAction(event)
  }
}