package com.github.andrewthehan.nomo.sample.ecs.entities

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.LinearFollowBehavior
import com.github.andrewthehan.nomo.core.ecs.types.Component
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Entity
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.CameraAttribute
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.CameraRefreshBehavior
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.PrimaryCameraBehavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.randomId

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.Gdx

private val cameraVelocity = 500f

fun createCamera(engine: Engine, target: Entity, entity: Entity = randomId()): Entity {
  val entityComponentManager = engine.managers.get<EntityComponentManager>()!!
  
  val components = arrayOf(
    Position2dAttribute(),
    Velocity2dAttribute(),
    PrimaryCameraBehavior(),
    CameraAttribute(OrthographicCamera(Gdx.graphics.getWidth().toFloat(), Gdx.graphics.getHeight().toFloat())),
    CameraRefreshBehavior(),
    LinearFollowBehavior(target, cameraVelocity)
  )
  entityComponentManager.add(entity, components)
  return entity
}