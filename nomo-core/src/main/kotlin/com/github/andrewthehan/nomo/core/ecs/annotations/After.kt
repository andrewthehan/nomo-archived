package com.github.andrewthehan.nomo.core.ecs.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class After(vararg val values: KClass<*>)