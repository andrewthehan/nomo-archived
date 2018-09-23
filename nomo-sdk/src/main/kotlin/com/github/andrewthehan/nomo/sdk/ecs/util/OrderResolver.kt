package com.github.andrewthehan.nomo.sdk.ecs.util

import com.github.andrewthehan.nomo.sdk.ecs.annotations.accepts
import com.github.andrewthehan.nomo.sdk.ecs.util.getBefores
import com.github.andrewthehan.nomo.sdk.ecs.util.getAfters
import com.github.andrewthehan.nomo.util.collections.Graph

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

fun <T : Any> getOrder(classes: Iterable<KClass<out T>>): List<KClass<out T>> {
  val graph = Graph<KClass<out T>>()

  classes
    .distinct()
    .forEach { graph.addNode(it) }
  
  classes.forEach { c ->
    val befores = c.getBefores()
    classes
      .filter { targetC -> befores.any { it.accepts(targetC) } }
      .forEach { graph.addEdge(c, it) }

    val afters = c.getAfters()
    classes
      .filter { target -> afters.any { it.accepts(target) } }
      .forEach { graph.addEdge(it, c) }
  }

  return graph.getTopologicalSort()
}

fun <C : Any, F : Any?> getFunctionOrder(classes: Iterable<KClass<out C>>, classToFunctionsMapper: (KClass<out C>) -> Iterable<KFunction<F>>): List<KFunction<F>> {
  val graph = Graph<KFunction<F>>()

  // create function nodes
  classes
    .flatMap { classToFunctionsMapper(it) }
    .distinct()
    .forEach { graph.addNode(it) }

  // create directed edges
  classes.forEach { c ->
    val functions = classToFunctionsMapper(c)

    // class befores
    val classBefores = c.getBefores()
    classes
      .filter { targetClass -> classBefores.any { it.accepts(targetClass) } }
      .flatMap { classToFunctionsMapper(it) }
      .forEach { targetFunction ->
        functions.forEach { graph.addEdge(it, targetFunction) }
      }

    // class afters
    val classAfters = c.getAfters()
    classes
      .filter { targetClass -> classAfters.any { it.accepts(targetClass) } }
      .flatMap { classToFunctionsMapper(it) }
      .forEach { targetFunction ->
        functions.forEach { graph.addEdge(targetFunction, it) }
      }

    functions.forEach { f ->
      // function befores
      val functionBefores = f.getBefores()
      classes
        .filter { targetClass -> functionBefores.any { it.accepts(targetClass) } }
        .flatMap { classToFunctionsMapper(it) }
        .forEach { targetFunction ->
          graph.addEdge(f, targetFunction)
        }
      
      // function afters
      val functionAfters = f.getAfters()
      classes
        .filter { targetClass -> functionAfters.any { it.accepts(targetClass) } }
        .flatMap { classToFunctionsMapper(it) }
        .forEach { targetFunction ->
          graph.addEdge(targetFunction, f)
        }
    }
  }

  return graph.getTopologicalSort()
}