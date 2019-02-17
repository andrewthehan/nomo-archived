package com.github.andrewthehan.nomo.sample.ecs.components.attributes

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.util.math.vectors.*

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Camera

@Dependent(Position2dAttribute::class)
class CameraAttribute(val camera: Camera) : AbstractAttribute(), Exclusive, Pendant {
  val batch = SpriteBatch()
  val renderer = ShapeRenderer()

  fun setPosition(position: MutableVector2f) {
    camera.position.x = position.x
    camera.position.y = position.y
    camera.update()
    batch.setProjectionMatrix(camera.combined)
    renderer.setProjectionMatrix(camera.combined)
  }
}