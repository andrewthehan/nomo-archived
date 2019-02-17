package com.github.andrewthehan.nomo.sample.ecs.systems

import com.github.andrewthehan.nomo.boot.io.MouseButton
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonHoldEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonReleaseEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MousePointerEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseWheelEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem
import com.github.andrewthehan.nomo.util.math.vectors.*

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer

fun toMouseButton(button: Int): MouseButton = when(button) {
  Buttons.LEFT -> MouseButton.LEFT
  Buttons.RIGHT -> MouseButton.RIGHT
  Buttons.MIDDLE -> MouseButton.MIDDLE
  Buttons.BACK -> MouseButton.BACK
  Buttons.FORWARD -> MouseButton.FORWARD
  else -> throw UnsupportedOperationException("Unsupported button: ${button}")
}

class MouseIoSystem : AbstractSystem {
  @MutableInject
  lateinit var eventManager: EventManager

  val heldMouseButtons = HashSet<MouseButton>()
  var lastPosition = zeroMutableVector2i()
  
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
      override fun touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        val mouseButton = toMouseButton(button)
        eventManager.dispatchEvent(MouseButtonPressEvent(mouseButton, mutableVectorOf(x, Gdx.graphics.getHeight() - y), pointer.toString()))
        heldMouseButtons.add(mouseButton)
        return true
      }

      override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        val mouseButton = toMouseButton(button)
        eventManager.dispatchEvent(MouseButtonReleaseEvent(mouseButton, mutableVectorOf(x, Gdx.graphics.getHeight() - y), pointer.toString()))
        heldMouseButtons.add(mouseButton)
        return true
      }

      override fun touchDragged(x: Int, y: Int, point: Int): Boolean {
        lastPosition = mutableVectorOf(x, Gdx.graphics.getHeight() - y)
        return true
      }

      override fun mouseMoved(x: Int, y: Int): Boolean {
        lastPosition = mutableVectorOf(x, Gdx.graphics.getHeight() - y)
        return true
      }

      override fun scrolled(amount: Int): Boolean {
        eventManager.dispatchEvent(MouseWheelEvent(amount, source))
        return true
      }
    })
  }

  override fun update(delta: Float) {
    eventManager.dispatchEvent(MousePointerEvent(mutableVectorOf(lastPosition.x.toInt(), lastPosition.y.toInt()), source))
    heldMouseButtons.forEach {
      eventManager.dispatchEvent(MouseButtonHoldEvent(it, mutableVectorOf(lastPosition.x.toInt(), lastPosition.y.toInt()), source))
    }
  }
}