package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.util.math.vectors.*

abstract class PositionAttribute() : AbstractAttribute(), Exclusive

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class Position1dAttribute(x: Float = 0f) : PositionAttribute(), MutableVector1f by mutableVectorOf(x)

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class Position2dAttribute(x: Float = 0f, y: Float = 0f) : PositionAttribute(), MutableVector2f by mutableVectorOf(x, y)

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class Position3dAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : PositionAttribute(), MutableVector3f by mutableVectorOf(x, y, z)