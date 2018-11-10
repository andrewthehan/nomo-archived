package com.github.andrewthehan.nomo.sample.ecs.entities

import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.DeathOnCollisionBehavior
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Acceleration2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors.KeyActionBehavior
import com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors.KeyPressActionBehavior
import com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors.KeyReleaseActionBehavior
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyPressEvent
import com.github.andrewthehan.nomo.boot.io.ecs.events.KeyReleaseEvent
import com.github.andrewthehan.nomo.boot.io.Key
import com.github.andrewthehan.nomo.boot.util.ecs.components.behaviors.PeriodicBehavior
import com.github.andrewthehan.nomo.boot.util.ecs.events.UpdateEvent
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.ShapeRenderBehavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Pendant
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.randomId

import com.badlogic.gdx.graphics.Color

import kotlin.math.abs
import kotlin.math.sign

private val playerVelocity = 3000f
private val deacceleration = -5f

fun createPlayer(engine: Engine, entity: Entity = randomId()): Entity {
  val entityComponentManager = engine.managers.get<EntityComponentManager>()!!

  val toAccelerations = { entities: Iterable<Entity> ->
    entities.map { entityComponentManager.getComponent<Acceleration2dAttribute>(it) }
  }

  val keyPressActionMap = mapOf(
    Key.W to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.y += playerVelocity } },
    Key.A to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.x -= playerVelocity } },
    Key.S to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.y -= playerVelocity } },
    Key.D to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.x += playerVelocity } }
  )
  val keyReleaseActionMap = mapOf(
    Key.W to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.y -= playerVelocity } },
    Key.A to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.x += playerVelocity } },
    Key.S to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.y += playerVelocity } },
    Key.D to { entities: Iterable<Entity> -> toAccelerations(entities).forEach { it.x -= playerVelocity } }
  )
  
  val components = arrayOf(
    Position2dAttribute(),
    Velocity2dAttribute(),
    Acceleration2dAttribute(),
    Shape2fAttribute(Circle(Vector2f(), 15f)),
    ShapeRenderBehavior(Color(1f, 1f, 1f, 1f)),
    CollidableAttribute(),
    DeathOnCollisionBehavior(),
    KeyPressActionBehavior(keyPressActionMap),
    KeyReleaseActionBehavior(keyReleaseActionMap),
    ShootingBehavior(),
    // smoothly deaccelerate
    object : AbstractBehavior() {
      @EventListener
      fun slowDown(event: UpdateEvent) {
        val entities = entityComponentManager[this]
        entities
          .map { entityComponentManager.getComponent<Velocity2dAttribute>(it) }
          .filter { !it.isZero() }
          .forEach {
            val delta = Vector2f(it.x, it.y) * deacceleration * event.delta
            it.x =
              if (abs(it.x) < abs(delta.x) && delta.x < 0.1f) { 0f }
              else { it.x + delta.x }
            it.y =
              if (abs(it.y) < abs(delta.y) && delta.y < 0.1f) { 0f }
              else { it.y + delta.y }
          }
      }
    }
  )
  entityComponentManager.add(entity, components)
  return entity
}

class ShootingBehavior : PeriodicBehavior(.1f), Pendant {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  var isShooting = false
  var direction = MutableVector2f()

  fun shoot() {
    val entity = entityComponentManager[this]
    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)
    val velocity = entityComponentManager.getComponent<Velocity2dAttribute>(entity)
    if (velocity.length() != 0f) {
      direction = velocity.normalized()
    }
    createBullet(entityComponentManager.engine, position = position, direction = direction)
  }

  @EventListener
  fun shoot(event: KeyPressEvent) {
    if (event.key == Key.SPACE) {
      isShooting = true
    }
  }

  @EventListener
  fun shoot(event: KeyReleaseEvent) {
    if (event.key == Key.SPACE) {
      isShooting = false
    }
  }

  override fun trigger() {
    if (isShooting) {
      shoot()
    }
  }
}