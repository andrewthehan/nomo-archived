package com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyHoldEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyReleaseEvent
import com.github.andrewthehan.nomo.boot.io.Key
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager

abstract class KeyActionBehavior(val keyActionMap: Map<Key, (Iterable<Entity>) -> Unit>) : AbstractBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  protected fun tryAction(event: KeyEvent) {
    if (!keyActionMap.containsKey(event.key)) {
      return
    }

    val entities = entityComponentManager[this]
    val action = keyActionMap.getValue(event.key)

    action(entities)
  }
}

class KeyPressActionBehavior(keyActionMap: Map<Key, (Iterable<Entity>) -> Unit>) : KeyActionBehavior(keyActionMap) {
  @EventListener
  fun action(event: KeyPressEvent) {
    tryAction(event)
  }
}

class KeyReleaseActionBehavior(keyActionMap: Map<Key, (Iterable<Entity>) -> Unit>) : KeyActionBehavior(keyActionMap) {
  @EventListener
  fun action(event: KeyReleaseEvent) {
    tryAction(event)
  }
}

class KeyHoldActionBehavior(keyActionMap: Map<Key, (Iterable<Entity>) -> Unit>) : KeyActionBehavior(keyActionMap) {
  @EventListener
  fun action(event: KeyHoldEvent) {
    tryAction(event)
  }
}