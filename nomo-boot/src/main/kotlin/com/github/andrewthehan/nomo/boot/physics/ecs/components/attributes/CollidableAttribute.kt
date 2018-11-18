package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

@Dependent(ShapeAttribute::class)
class CollidableAttribute() : AbstractAttribute(), Exclusive