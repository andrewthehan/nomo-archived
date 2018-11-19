package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.CameraAttribute
import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager

import com.badlogic.gdx.graphics.Texture;

@Dependent(Position2dAttribute::class)
class ImageRenderBehavior(var texture: Texture) : RenderBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  override fun render(event: RenderEvent) {
    val batch = entityComponentManager.getComponent<CameraAttribute>(event.camera).batch
    val entities = entityComponentManager[this]
    batch.begin()
    entities.forEach {
      val position = entityComponentManager.getComponent<Position2dAttribute>(it)
      batch.draw(texture, position.x, position.y)
    }
    batch.end()
  }
}