package com.github.andrewthehan.nomo.boot.physics.ecs.systems

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Acceleration2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Acceleration3dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Position3dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Velocity3dAttribute
import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem
import com.github.andrewthehan.nomo.util.math.MutableVector

class PhysicsStepSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  private inline fun <reified A, reified B> step(delta: Float) where
      A : Attribute,
      A : MutableVector<Float>,
      A : Exclusive,
      B : Attribute,
      B : MutableVector<Float>,
      B : Exclusive {
    entityComponentManager
      .getComponents<A>()
      .filter { !it.isZero() }
      .forEach { a ->
        val entities = entityComponentManager.getEntities(a)
        entities.forEach { entity ->
          val b = entityComponentManager.getComponent<B>(entity)
          b += a * delta
        }
      }
  }

  override fun update(delta: Float) {
    step<Velocity2dAttribute, Position2dAttribute>(delta)
    step<Acceleration2dAttribute, Velocity2dAttribute>(delta)
    step<Velocity3dAttribute, Position3dAttribute>(delta)
    step<Acceleration3dAttribute, Velocity3dAttribute>(delta)
  }
}