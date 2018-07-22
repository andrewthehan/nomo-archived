package com.github.andrewthehan.nomo.util

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.KAnnotatedElement

inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation() = findAnnotation<T>() != null

inline fun <reified T : Annotation> KAnnotatedElement.getAnnotation() = findAnnotation<T>()!!
