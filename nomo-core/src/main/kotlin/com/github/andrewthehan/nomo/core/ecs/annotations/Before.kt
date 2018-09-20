package com.github.andrewthehan.nomo.core.ecs.annotations

import kotlin.reflect.KClass

@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Repeatable
@java.lang.annotation.Repeatable(Befores::class)
annotation class Before(val include: Array<KClass<*>>, val exclude: Array<KClass<*>> = emptyArray<KClass<*>>())

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Befores(vararg val values: Before)