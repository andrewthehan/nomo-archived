package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.getAnnotation

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

data class EventListenerInfo(val behavior: Behavior, val eventListener: KFunction<*>) {
  val eventType = eventListener.getAnnotation<EventListener>().value
}