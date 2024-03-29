package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.CameraAttribute
import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager

import com.badlogic.gdx.graphics.g2d.BitmapFont

@Dependent(Position2dAttribute::class)
class TextRenderBehavior(var text: String = "") : RenderBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  private val font = BitmapFont()

  @EventListener
  override fun render(event: RenderEvent) {
    val batch = entityComponentManager.getComponent<CameraAttribute>(event.camera).batch
    val entities = entityComponentManager[this]
    batch.begin()
    entities.forEach {
      val position = entityComponentManager.getComponent<Position2dAttribute>(it)
      font.draw(batch, text, position.x, position.y)
    }
    batch.end()
  }
}