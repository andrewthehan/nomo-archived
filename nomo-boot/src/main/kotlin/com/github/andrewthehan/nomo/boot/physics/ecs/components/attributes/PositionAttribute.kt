package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

class Position1dAttribute(x: Float = 0f) : MutableVector1fAttribute(x), Exclusive

class Position2dAttribute(x: Float = 0f, y: Float = 0f) : MutableVector2fAttribute(x, y), Exclusive

class Position3dAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3fAttribute(x, y, z), Exclusive