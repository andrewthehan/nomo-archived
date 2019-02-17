package com.github.andrewthehan.nomo.sample.ecs.systems

import com.github.andrewthehan.nomo.boot.io.Key
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyHoldEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyReleaseEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer

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
  else -> throw UnsupportedOperationException("Unsupported keyCode: ${Keys.toString(keyCode)} (${keyCode})")
}

class KeyIoSystem : AbstractSystem {
  @MutableInject
  lateinit var eventManager: EventManager

  val heldKeys = HashSet<Key>()
  
  val source = "0"

  constructor() : super(){
    var multiplexer = Gdx.input.getInputProcessor()
    if (multiplexer == null) {
      multiplexer = InputMultiplexer()
      Gdx.input.setInputProcessor(multiplexer)
    } else if (multiplexer !is InputMultiplexer) {
      val processor = multiplexer
      multiplexer = InputMultiplexer()
      multiplexer.addProcessor(processor)
      Gdx.input.setInputProcessor(multiplexer)
    }
    multiplexer.addProcessor(object : InputAdapter() {
      override fun keyDown(keyCode: Int): Boolean {
        val key = toKey(keyCode)
        eventManager.dispatchEvent(KeyPressEvent(key, source))
        heldKeys.add(key)
        return true
      }

      override fun keyUp(keyCode: Int): Boolean {
        val key = toKey(keyCode)
        eventManager.dispatchEvent(KeyReleaseEvent(key, source))
        heldKeys.remove(key)
        return true
      }
    })
  }

  override fun update(delta: Float) {
    heldKeys.forEach {
      eventManager.dispatchEvent(KeyHoldEvent(it, source))
    }
  }
}