package com.github.andrewthehan.nomo.sample.ecs.entities

import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.DeathOnCollisionBehavior
import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.RemoveOnDeathBehavior
import com.github.andrewthehan.nomo.boot.layer.ecs.components.attributes.LayerAttribute
import com.github.andrewthehan.nomo.boot.layer.Layer
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.CollidableAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Shape2fAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.FollowBehavior
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.PlayerAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.ShapeRenderBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.randomId

import com.badlogic.gdx.graphics.Color

fun createEnemy(engine: Engine): EcsId {
  val entityComponentManager = engine.managers.get<EntityComponentManager>()!!

  val playerAttributes = entityComponentManager.getComponents<PlayerAttribute>()
  val players = playerAttributes.flatMap { entityComponentManager[it] }.distinct()
  val positions = players.map { entityComponentManager.getComponent<Position2dAttribute>(it) }

  val spawnPosition = Vector2f(1000f, 1000f)
  val closestPosition = positions.maxBy { (spawnPosition - it).length() }!!
  val closestPlayer = entityComponentManager[closestPosition].single()

  val components = arrayOf(
    Position2dAttribute(spawnPosition.x, spawnPosition.y),
    Velocity2dAttribute(),
    Shape2fAttribute(RegularPolygon(Vector2f(), 20f, 3)),
    ShapeRenderBehavior(Color(0f, .7f, .7f, 1f)),
    CollidableAttribute(),
    FollowBehavior(closestPlayer, 200f),
    DeathOnCollisionBehavior(),
    RemoveOnDeathBehavior(),
    LayerAttribute(Layer("enemy")),
    LayerAttribute(Layer("player"))
  )

  val id = randomId()
  entityComponentManager.add(id, components)
  return id
}