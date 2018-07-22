package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.types.EcsObject
import com.github.andrewthehan.nomo.core.ecs.types.Event
import com.github.andrewthehan.nomo.util.hasAnnotation

import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty

fun <ActualBehavior: Behavior> getEventListeners(behaviorClass: KClass<ActualBehavior>)
  = behaviorClass.functions.filter { it.hasAnnotation<EventListener>() }

fun getInjectableManagerProperties(ecsObjectClass: KClass<out EcsObject>)
  = ecsObjectClass.memberProperties.filter { it.hasAnnotation<MutableInject>() }.map { it as KMutableProperty<*> }