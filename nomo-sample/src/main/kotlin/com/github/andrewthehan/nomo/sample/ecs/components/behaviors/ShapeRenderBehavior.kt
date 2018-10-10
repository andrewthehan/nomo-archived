package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.vectors.*

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color

@Dependent(Position2dAttribute::class, Shape2fAttribute::class)
class ShapeRenderBehavior(var color: Color = Color(1f, 1f, 1f, 1f)) : RenderBehavior() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  private val renderer = ShapeRenderer()

  @EventListener
  override fun render(event: RenderEvent) {
    val entities = entityComponentManager[this]
    renderer.begin(ShapeRenderer.ShapeType.Line)
    entities.forEach { entity ->
      val position: MutableVector2f = entityComponentManager.getComponent<Position2dAttribute>(entity)
      val shapes = entityComponentManager.getComponents<Shape2fAttribute>(entity)
      shapes.forEach { shape ->
        val locations = shape.points.map { it + position }
        renderer.setColor(color)
        renderer.polygon(locations.flatMap { it.components }.toFloatArray())
      }
    }
    renderer.end()
  }
}