package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.util.math.vectors.*

@Dependent(PositionAttribute::class)
abstract class VelocityAttribute() : AbstractAttribute(), Exclusive

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Dependent(Position1dAttribute::class)
class Velocity1dAttribute(x: Float = 0f) : VelocityAttribute(), MutableVector1f by mutableVectorOf(x)

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Dependent(Position2dAttribute::class)
class Velocity2dAttribute(x: Float = 0f, y: Float = 0f) : VelocityAttribute(), MutableVector2f by mutableVectorOf(x, y)

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Dependent(Position3dAttribute::class)
class Velocity3dAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : VelocityAttribute(), MutableVector3f by mutableVectorOf(x, y, z)