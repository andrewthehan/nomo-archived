package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.util.math.vectors.*

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class MassAttribute(mass: Float) : AbstractAttribute(), Exclusive, MutableScalarF by mutableScalarOf(mass)
