package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

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

@Dependent(CameraAttribute::class)
@Befores(
  Before(include = [ Behavior::class ], exclude = [ CameraRefreshBehavior::class ])
)
class CameraRefreshBehavior() : AbstractBehavior(), Exclusive, Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  fun updateCamera(event: UpdateEvent) {
    val entity = entityComponentManager[this]
    val camera = entityComponentManager.getComponent<CameraAttribute>(entity)
    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)

    camera.setPosition(position)
  }
}