package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.CameraAttribute
import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color

@Dependent(Position2dAttribute::class, Shape2fAttribute::class)
class ShapeRenderBehavior(var color: Color = Color(1f, 1f, 1f, 1f)) : RenderBehavior(), Exclusive {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  override fun render(event: RenderEvent) {
    val camera = entityComponentManager.getComponent<CameraAttribute>(event.camera)
    val renderer = camera.renderer

    val entities = entityComponentManager[this]
    renderer.begin(ShapeRenderer.ShapeType.Line)
    entities.forEach { entity ->
      val position: MutableVector2f = entityComponentManager.getComponent<Position2dAttribute>(entity)
      val shapes = entityComponentManager.getComponents<Shape2fAttribute>(entity)
      shapes.forEach { shape ->
        renderer.setColor(color)
        if (shape is Circle<*, *>) {
          renderer.circle(shape.center.x, shape.center.y, shape.radius.toFloat())
        } else {
          val locations = shape.points.map { it + position }
          renderer.polygon(locations.flatMap { it.components }.toFloatArray())
        }
      }
    }
    renderer.end()
  }
}