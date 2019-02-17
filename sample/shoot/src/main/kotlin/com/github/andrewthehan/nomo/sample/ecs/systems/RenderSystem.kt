package com.github.andrewthehan.nomo.sample.ecs.systems

import com.github.andrewthehan.nomo.sample.ecs.components.attributes.CameraAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.RenderBehavior
import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.Gdx

class RenderSystem() : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @MutableInject
  lateinit var eventManager: EventManager

  override fun update(delta: Float) {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    val renders = entityComponentManager.getComponents<RenderBehavior>()
    val cameras = entityComponentManager.getComponents<CameraAttribute>()
    
    cameras
      .map { entityComponentManager[it] }
      .forEach { eventManager.dispatchEvent(RenderEvent(it), renders) }
  }
}