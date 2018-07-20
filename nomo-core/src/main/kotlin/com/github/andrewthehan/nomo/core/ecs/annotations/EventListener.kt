package com.github.andrewthehan.nomo.core.ecs.annotations

import com.github.andrewthehan.nomo.core.ecs.types.Event

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
annotation class EventListener(val value: KClass<out Event>)