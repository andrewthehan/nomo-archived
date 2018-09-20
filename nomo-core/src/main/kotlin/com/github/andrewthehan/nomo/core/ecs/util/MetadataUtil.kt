package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.After
import com.github.andrewthehan.nomo.core.ecs.annotations.Afters
import com.github.andrewthehan.nomo.core.ecs.annotations.Before
import com.github.andrewthehan.nomo.core.ecs.annotations.Befores
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

fun KClass<*>.getDependencies()
  = findAnnotation<Dependent>()?.values.orEmpty()

fun KClass<*>.getEventListeners()
  = functions.filter { it.hasAnnotation<EventListener>() }

fun KClass<*>.getInjectableProperties()
  = memberProperties.filter { it.hasAnnotation<MutableInject>() }.map { it as KMutableProperty<*> }

fun KClass<*>.getBefores()
  = findAnnotation<Befores>()?.values.orEmpty()

fun KClass<*>.getAfters()
  = findAnnotation<Afters>()?.values.orEmpty()
  
fun KFunction<*>.getBefores()
  = findAnnotation<Befores>()?.values.orEmpty()

fun KFunction<*>.getAfters()
  = findAnnotation<Afters>()?.values.orEmpty()