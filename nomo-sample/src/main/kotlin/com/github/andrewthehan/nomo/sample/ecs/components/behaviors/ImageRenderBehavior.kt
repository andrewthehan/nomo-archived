package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.sample.ecs.components.attributes.PositionAttribute
import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager

import com.badlogic.gdx.graphics.Texture;

import ktx.graphics.*

@Dependent(PositionAttribute::class)
class ImageRenderBehavior(var texture: Texture) : RenderBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  override fun render(event: RenderEvent) {
    val batch = event.batch
    val entities = entityComponentManager.getEntities(this)
    batch.use {
      entities.forEach {
        val position = entityComponentManager.getComponent<PositionAttribute>(it)
        batch.draw(texture, position.x, position.y)
      }
    }
  }
}