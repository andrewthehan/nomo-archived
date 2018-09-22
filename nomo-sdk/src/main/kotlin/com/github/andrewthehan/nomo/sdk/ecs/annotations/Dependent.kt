package com.github.andrewthehan.nomo.sdk.ecs.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class Dependent(vararg val values: KClass<*>)