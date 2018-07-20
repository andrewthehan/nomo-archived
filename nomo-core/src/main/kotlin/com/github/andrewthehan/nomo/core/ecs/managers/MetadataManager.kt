package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.util.hasAnnotation

import kotlin.reflect.full.functions
import kotlin.reflect.KFunction

class MetadataManager(val ecsManager: EcsManager) {

  inline fun <reified ActualBehavior: Behavior> getEventListeners(behavior: ActualBehavior)
    = (behavior::class).functions.filter { it.hasAnnotation<EventListener>() }
}
