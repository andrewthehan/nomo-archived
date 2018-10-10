package com.github.andrewthehan.nomo.sdk.ecs.util

import com.github.andrewthehan.nomo.sdk.ecs.util.getDependencies

import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.KClass

fun hasDependencies(classes: Iterable<KClass<*>>): Boolean {
  return classes
    .map { it.getDependencies() }
    .filter { it.any() }
    .none {
      it.any { dependency -> classes.none { it.isSubclassOf(dependency) } }
    }
}

fun getMissingDependencies(classes: Iterable<KClass<*>>): Iterable<KClass<*>> {
  return classes
    .map { it.getDependencies() }
    .filter { it.any() }
    .flatMap {
      it.filter { dependency -> classes.none { it.isSubclassOf(dependency) } }
    }
}