package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

@Dependent(PositionAttribute::class)
abstract class BodyAttribute() : AbstractAttribute(), Exclusive

class StaticBodyAttribute() : BodyAttribute()

@Dependent(VelocityAttribute::class)
class KinematicBodyAttribute() : BodyAttribute()

@Dependent(AccelerationAttribute::class)
class DynamicBodyAttribute() : BodyAttribute()