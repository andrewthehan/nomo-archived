package com.github.andrewthehan.nomo.sample.ecs.entities

import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.DeathOnCollisionBehavior
import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.RemoveOnDeathBehavior
import com.github.andrewthehan.nomo.boot.layer.ecs.components.attributes.LayerAttribute
import com.github.andrewthehan.nomo.boot.layer.Layer
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Acceleration2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.DynamicBodyAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.MassAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.ShapeAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.Kinetic2dBehavior
import com.github.andrewthehan.nomo.boot.physics.ecs.events.Force2dEvent
import com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors.KeyHoldActionBehavior
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyReleaseEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MouseButtonReleaseEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.MousePointerEvent
import com.github.andrewthehan.nomo.boot.io.Key
import com.github.andrewthehan.nomo.boot.io.MouseButton
import com.github.andrewthehan.nomo.boot.time.ecs.components.behaviors.PeriodicBehavior
import com.github.andrewthehan.nomo.boot.time.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.PlayerAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.ShapeRenderBehavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.randomId

import com.badlogic.gdx.graphics.Color

import kotlin.math.abs
import kotlin.math.sign

private val PLAYER_MOVE_FORCE = 300f
private val PLAYER_MASS = 1f

fun createPlayer(engine: Engine, entity: Entity = randomId()): Entity {
  val entityComponentManager = engine.managers.get<EntityComponentManager>()!!
  val eventManager = engine.managers.get<EventManager>()!!

  val keyActionMap = mapOf(
    Key.W to { entities: Iterable<Entity> -> eventManager.dispatchEvent(Force2dEvent(vectorOf(0f, PLAYER_MOVE_FORCE)), entities) },
    Key.A to { entities: Iterable<Entity> -> eventManager.dispatchEvent(Force2dEvent(vectorOf(-PLAYER_MOVE_FORCE, 0f)), entities) },
    Key.S to { entities: Iterable<Entity> -> eventManager.dispatchEvent(Force2dEvent(vectorOf(0f, -PLAYER_MOVE_FORCE)), entities) },
    Key.D to { entities: Iterable<Entity> -> eventManager.dispatchEvent(Force2dEvent(vectorOf(PLAYER_MOVE_FORCE, 0f)), entities) }
  )
  
  val components = arrayOf(
    PlayerAttribute(),
    Position2dAttribute(),
    Velocity2dAttribute(),
    Acceleration2dAttribute(),
    ShapeAttribute(Circle(zeroVector2f(), 15f)),
    ShapeRenderBehavior(Color(1f, 1f, 1f, 1f)),
    CollidableAttribute(),
    DeathOnCollisionBehavior(),
    RemoveOnDeathBehavior(),
    LayerAttribute(Layer("player")),
    KeyHoldActionBehavior(keyActionMap),
    ShootingBehavior(),
    DynamicBodyAttribute(),
    MassAttribute(PLAYER_MASS),
    Kinetic2dBehavior()
  )
  entityComponentManager.add(entity, components)
  return entity
}

class ShootingBehavior : PeriodicBehavior(.1f), Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  var isShooting = false
  lateinit var target: Vector2f

  fun shoot() {
    val entity = entityComponentManager[this]
    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)
    val direction = (target - position).normalized()
    createBullet(entityComponentManager.engine, position = position, direction = direction)
  }

  @EventListener
  fun shoot(event: MouseButtonPressEvent) {
    if (event.mouseButton == MouseButton.LEFT) {
      isShooting = true
    }
  }

  @EventListener
  fun shoot(event: MouseButtonReleaseEvent) {
    if (event.mouseButton == MouseButton.LEFT) {
      isShooting = false
    }
  }

  @EventListener
  fun shoot(event: MousePointerEvent) {
    target = vectorOf(event.position.x.toFloat(), event.position.y.toFloat())
  }

  override fun trigger() {
    if (isShooting) {
      shoot()
    }
  }
}