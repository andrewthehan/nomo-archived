package com.github.andrewthehan.nomo.reflect

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.KAnnotatedElement

inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation() = findAnnotation<T>() != null
