package com.github.andrewthehan.nomo.core.ecs.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class Dependent(vararg val values: KClass<*>)