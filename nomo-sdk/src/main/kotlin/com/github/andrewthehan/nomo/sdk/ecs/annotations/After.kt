package com.github.andrewthehan.nomo.sdk.ecs.annotations

import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.KClass

@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Repeatable
@java.lang.annotation.Repeatable(Afters::class)
annotation class After(val include: Array<KClass<*>>, val exclude: Array<KClass<*>> = emptyArray<KClass<*>>())

fun After.accepts(c: KClass<*>) = include.any { c.isSubclassOf(it) } && exclude.none { c.isSubclassOf(it) }

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Afters(vararg val values: After)