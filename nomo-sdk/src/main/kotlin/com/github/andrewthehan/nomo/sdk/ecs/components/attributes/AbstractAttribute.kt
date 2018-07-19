package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.util.randomId

abstract class AbstractAttribute(id: String = randomId()) : Attribute(id)