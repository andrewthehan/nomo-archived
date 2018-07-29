package com.github.andrewthehan.nomo.core.ecs.annotations

import com.github.andrewthehan.nomo.core.ecs.types.Event

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class Dependent(vararg val values: KClass<*>)