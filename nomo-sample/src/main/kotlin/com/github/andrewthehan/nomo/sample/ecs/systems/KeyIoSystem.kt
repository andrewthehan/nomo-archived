package com.github.andrewthehan.nomo.sample.ecs.systems

import com.github.andrewthehan.nomo.boot.io.Key
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyHeldEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyPressedEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyReleasedEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter

fun toKey(keyCode: Int): Key = when(keyCode) {
  Keys.A -> Key.A
  Keys.B -> Key.B
  Keys.C -> Key.C
  Keys.D -> Key.D
  Keys.E -> Key.E
  Keys.F -> Key.F
  Keys.G -> Key.G
  Keys.H -> Key.H
  Keys.I -> Key.I
  Keys.J -> Key.J
  Keys.K -> Key.K
  Keys.L -> Key.L
  Keys.M -> Key.M
  Keys.N -> Key.N
  Keys.O -> Key.O
  Keys.P -> Key.P
  Keys.Q -> Key.Q
  Keys.R -> Key.R
  Keys.S -> Key.S
  Keys.T -> Key.T
  Keys.U -> Key.U
  Keys.V -> Key.V
  Keys.W -> Key.W
  Keys.X -> Key.X
  Keys.Y -> Key.Y
  Keys.Z -> Key.Z
  Keys.SPACE -> Key.SPACE
  Keys.CONTROL_LEFT -> Key.CONTROL_LEFT
  Keys.CONTROL_RIGHT -> Key.CONTROL_RIGHT
  Keys.ALT_LEFT -> Key.ALT_LEFT
  Keys.ALT_RIGHT -> Key.ALT_RIGHT
  Keys.SHIFT_LEFT -> Key.SHIFT_LEFT
  Keys.SHIFT_RIGHT -> Key.SHIFT_RIGHT
  else -> throw UnsupportedOperationException("Unsupported keyCode: ${keyCode}")
}

class KeyIoSystem : AbstractSystem {
  @MutableInject
  lateinit var eventManager: EventManager

  val heldKeys = HashSet<Key>()
  
  val source = "0"

  constructor() : super(){
    Gdx.input.setInputProcessor(object : InputAdapter() {
      override fun keyDown(keyCode: Int): Boolean {
        val key = toKey(keyCode)
        eventManager.dispatchEvent(KeyPressedEvent(key, source))
        heldKeys.add(key)
        return true
      }

      override fun keyUp(keyCode: Int): Boolean {
        val key = toKey(keyCode)
        eventManager.dispatchEvent(KeyReleasedEvent(key, source))
        heldKeys.remove(key)
        return true
      }
    })
  }

  override fun update(delta: Float) {
    heldKeys.forEach {
      eventManager.dispatchEvent(KeyHeldEvent(it, source))
    }
  }
}