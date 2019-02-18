package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.util.math.vectors.*

@Dependent(VelocityAttribute::class)
abstract class AccelerationAttribute() : AbstractAttribute(), Exclusive

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Dependent(Velocity1dAttribute::class)
class Acceleration1dAttribute(x: Float = 0f) : AccelerationAttribute(), MutableVector1f by mutableVectorOf(x)

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Dependent(Velocity2dAttribute::class)
class Acceleration2dAttribute(x: Float = 0f, y: Float = 0f) : AccelerationAttribute(), MutableVector2f by mutableVectorOf(x, y)

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Dependent(Velocity3dAttribute::class)
class Acceleration3dAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : AccelerationAttribute(), MutableVector3f by mutableVectorOf(x, y, z)