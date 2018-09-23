package com.github.andrewthehan.nomo.sdk.ecs.util

import com.github.andrewthehan.nomo.sdk.ecs.util.getDependencies

import kotlin.reflect.KClass

fun hasDependencies(classes: Iterable<KClass<*>>): Boolean {
  return classes
    .map { it.getDependencies() }
    .filter { it.any() }
    .none {
      val missingDependencies = it.subtract(classes)
      missingDependencies.any()
    }
}

fun getMissingDependencies(classes: Iterable<KClass<*>>): Iterable<KClass<*>> {
  return classes
    .map { it.getDependencies() }
    .filter { it.any() }
    .flatMap {
      it.subtract(classes)
    }
}