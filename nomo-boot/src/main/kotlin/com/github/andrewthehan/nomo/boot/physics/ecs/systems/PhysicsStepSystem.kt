package com.github.andrewthehan.nomo.boot.physics.ecs.systems

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.*
import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem
import com.github.andrewthehan.nomo.util.math.*

class PhysicsStepSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  private inline fun <VectorType : MutableVector<*, *>, reified A, reified B> step(delta: Float) where
      A : MutableVector<Float, VectorType>,
      A : Attribute,
      A : Exclusive,
      B : MutableVector<Float, VectorType>,
      B : Attribute,
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
    step<MutableVector2f, Velocity2dAttribute, Position2dAttribute>(delta)
    step<MutableVector2f, Acceleration2dAttribute, Velocity2dAttribute>(delta)
    step<MutableVector3f, Velocity3dAttribute, Position3dAttribute>(delta)
    step<MutableVector3f, Acceleration3dAttribute, Velocity3dAttribute>(delta)
  }
}