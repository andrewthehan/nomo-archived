package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.boot.util.ecs.components.attributes.MutableVector2fAttribute
import com.github.andrewthehan.nomo.boot.util.ecs.components.attributes.MutableVector3fAttribute
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

@Dependent(Position2dAttribute::class)
class Velocity2dAttribute(x: Float = 0f, y: Float = 0f) : MutableVector2fAttribute(x, y), Exclusive

@Dependent(Position3dAttribute::class)
class Velocity3dAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3fAttribute(x, y, z), Exclusive