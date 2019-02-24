package com.github.andrewthehan.nomo.boot.physics.ecs.systems

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.*
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.Kinetic2dBehavior
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.Kinetic3dBehavior
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.KineticBehavior
import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem
import com.github.andrewthehan.nomo.util.math.vectors.*

class PhysicsStepSystem : AbstractSystem() {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  private inline fun <VectorType : MutableVector<Float>, reified A, reified B> computeAcceleration() where
      A : MutableVector<Float>,
      A : Attribute,
      A : Exclusive,
      A : AccelerationAttribute,
      B : Behavior,
      B : Exclusive,
      B : KineticBehavior<VectorType> {
    entityComponentManager
      .getComponents<DynamicBodyAttribute>()
      .flatMap { entityComponentManager[it] }
      .forEach {
        val acceleration = entityComponentManager.getComponent<A>(it)
        val kinetic = entityComponentManager.getComponent<B>(it)
        val mass = entityComponentManager.getComponent<MassAttribute>(it)
        acceleration += kinetic.netForce / mass

        kinetic.reset()
      };
  }

  private inline fun <VectorType : Vector<Float>, reified A, reified B> step(delta: Float) where
      A : MutableVector<Float>,
      A : Attribute,
      A : Exclusive,
      B : MutableVector<Float>,
      B : Attribute,
      B : Exclusive {
    entityComponentManager
      .getComponents<A>()
      .filter { !it.isZero() }
      .forEach { a ->
        val entities = entityComponentManager[a]
        entities.forEach { entity ->
          val b = entityComponentManager.getComponent<B>(entity)
          b += a * delta
        }
      }
  }

  override fun update(delta: Float) {
    computeAcceleration<MutableVector2f, Acceleration2dAttribute, Kinetic2dBehavior>()
    step<Vector2f, Velocity2dAttribute, Position2dAttribute>(delta)
    step<Vector2f, Acceleration2dAttribute, Velocity2dAttribute>(delta)

    computeAcceleration<MutableVector3f, Acceleration3dAttribute, Kinetic3dBehavior>()
    step<Vector3f, Velocity3dAttribute, Position3dAttribute>(delta)
    step<Vector3f, Acceleration3dAttribute, Velocity3dAttribute>(delta)
  }
}