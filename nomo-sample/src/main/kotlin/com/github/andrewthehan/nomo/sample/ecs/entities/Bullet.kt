package com.github.andrewthehan.nomo.sample.ecs.entities

import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.DeathOnCollisionBehavior
import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.DelayedDeathBehavior
import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.RemoveOnDeathBehavior
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.util.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.ShapeRenderBehavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.randomId

import com.badlogic.gdx.graphics.Color

private val bulletVelocity = 2000f

fun createBullet(engine: Engine, entity: Entity = randomId(), position: MutableVector2f, direction: Vector2f): Entity {
  val entityComponentManager = engine.managers.get<EntityComponentManager>()!!
  
  val velocity = direction * bulletVelocity

  val components = arrayOf(
    Position2dAttribute(position.x, position.y),
    Velocity2dAttribute(velocity.x, velocity.y),
    Shape2fAttribute(Circle(Vector2f(), 2f)),
    ShapeRenderBehavior(Color(1f, 1f, 1f, 1f)),
    CollidableAttribute(),
    // DeathOnCollisionBehavior(),
    DelayedDeathBehavior(1f),
    RemoveOnDeathBehavior()
  )
  entityComponentManager.add(entity, components)
  return entity
}