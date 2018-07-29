package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.util.hasAnnotation

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty

fun getDependencies(c: KClass<*>)
  = c.findAnnotation<Dependent>()?.values ?: emptyArray()

fun getEventListeners(c: KClass<*>)
  = c.functions.filter { it.hasAnnotation<EventListener>() }

fun getInjectableProperties(c: KClass<*>)
  = c.memberProperties.filter { it.hasAnnotation<MutableInject>() }.map { it as KMutableProperty<*> }