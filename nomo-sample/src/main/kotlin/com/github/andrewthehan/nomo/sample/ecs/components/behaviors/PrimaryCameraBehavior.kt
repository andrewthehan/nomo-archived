package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MousePointerEvent
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.time.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.CameraAttribute
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Before
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Befores
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.vectors.*

import com.badlogic.gdx.Gdx

@Dependent(CameraAttribute::class)
@Befores(
  Before(include = [ Behavior::class ], exclude = [ PrimaryCameraBehavior::class, CameraRefreshBehavior::class ])
)
class PrimaryCameraBehavior() : AbstractBehavior(), Exclusive, Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager
  
  @EventListener
  private fun updateMousePosition(position: MutableVector2i) {
    val entity = entityComponentManager[this]
    val camera = entityComponentManager.getComponent<CameraAttribute>(entity)
    val cameraPosition = entityComponentManager.getComponent<Position2dAttribute>(entity)

    position += cameraPosition.toMutableVector2i() - MutableVector2i(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)
  }
  fun updateMousePosition(event: MouseButtonEvent) {
    updateMousePosition(event.position)
  }

  @EventListener
  fun updateMousePosition(event: MousePointerEvent) {
    updateMousePosition(event.position)
  }
}