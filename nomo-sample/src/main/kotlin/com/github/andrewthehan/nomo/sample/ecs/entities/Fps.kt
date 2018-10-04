package com.github.andrewthehan.nomo.sample.ecs.entities

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.TextRenderBehavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.PeriodicBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.randomId

import com.badlogic.gdx.Gdx

import ktx.graphics.*

val textUpdateBehavior = object : PeriodicBehavior(1f) {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  override fun trigger() {
    val fps = Gdx.graphics.getFramesPerSecond()

    val entities = entityComponentManager.getEntities(this)
    entities.forEach {
      val textRenders = entityComponentManager.getComponents<TextRenderBehavior>()
      textRenders.forEach {
        it.text = "${fps} fps"
      }
    }
  }
}

fun create(engine: Engine, x: Float, y: Float, entity: Entity = randomId()): Entity {
  val components = arrayOf(
    Position2dAttribute(x, y),
    TextRenderBehavior(),
    textUpdateBehavior
  )
  engine.managers.get<EntityComponentManager>()!!.add(entity, components)
  return entity
}