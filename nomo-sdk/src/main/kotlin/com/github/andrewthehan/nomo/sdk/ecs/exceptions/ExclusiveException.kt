package com.github.andrewthehan.nomo.sdk.ecs.exceptions

import kotlin.reflect.KClass

class ExclusiveException(target: Any, type: KClass<out Any>) : Exception("Target (${target}) already has type (${type}) bound")