package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

@Dependent(Position1dAttribute::class)
class Velocity1dAttribute(x: Float = 0f) : MutableVector1fAttribute(x), Exclusive

@Dependent(Position2dAttribute::class)
class Velocity2dAttribute(x: Float = 0f, y: Float = 0f) : MutableVector2fAttribute(x, y), Exclusive

@Dependent(Position3dAttribute::class)
class Velocity3dAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3fAttribute(x, y, z), Exclusive