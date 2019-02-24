package com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.Acceleration2dAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.DynamicBodyAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.MassAttribute
import com.github.andrewthehan.nomo.boot.physics.ecs.events.Force2dEvent
import com.github.andrewthehan.nomo.boot.physics.ecs.events.Force3dEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.util.math.vectors.*

@Dependent(DynamicBodyAttribute::class, MassAttribute::class)
abstract class KineticBehavior<out MutableVectorType : MutableVector<Float>>() : AbstractBehavior(), Exclusive {
  abstract val netForce: MutableVectorType

  fun reset() {
    netForce.zero()
  }
}

@Dependent(DynamicBodyAttribute::class, MassAttribute::class)
class Kinetic2dBehavior() : KineticBehavior<MutableVector2f>() {
  override val netForce = zeroMutableVector2f()

  @EventListener
  fun react(event: Force2dEvent) {
    netForce += event.newtons
  }
}

@Dependent(DynamicBodyAttribute::class, MassAttribute::class)
class Kinetic3dBehavior() : KineticBehavior<MutableVector3f>() {
  override val netForce = zeroMutableVector3f()

  @EventListener
  fun react(event: Force3dEvent) {
    netForce += event.newtons
  }
}