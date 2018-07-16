package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.core.ecs.types.EcsId

open class NumberAttribute<ValueType : Number>(var value: ValueType) : Attribute(EcsId(0))