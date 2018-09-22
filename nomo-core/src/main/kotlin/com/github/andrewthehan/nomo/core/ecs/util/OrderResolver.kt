package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.accepts
import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.util.getBefores
import com.github.andrewthehan.nomo.core.ecs.util.getAfters
import com.github.andrewthehan.nomo.util.collections.Graph
import com.github.andrewthehan.nomo.util.collections.MultiMap

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

fun getEventListenerOrder(behaviorClasses: Iterable<KClass<out Behavior>>): List<KFunction<*>> {
  val graph = Graph<KFunction<*>>()

  // create event listener nodes
  behaviorClasses
    .flatMap { it.getEventListeners() }
    .distinct()
    .forEach { graph.addNode(it) }

  // create directed edges
  behaviorClasses.forEach { behaviorClass ->
    val eventListeners = behaviorClass.getEventListeners()

    // class befores
    val classBefores = behaviorClass.getBefores()
    behaviorClasses
      .filter { targetBehaviorClass -> classBefores.any { it.accepts(targetBehaviorClass) } }
      .flatMap { it.getEventListeners() }
      .forEach { targetEventListener ->
        eventListeners.forEach { graph.addEdge(it, targetEventListener) }
      }

    // class afters
    val classAfters = behaviorClass.getAfters()
    behaviorClasses
      .filter { targetBehaviorClass -> classAfters.any { it.accepts(targetBehaviorClass) } }
      .flatMap { it.getEventListeners() }
      .forEach { targetEventListener ->
        eventListeners.forEach { graph.addEdge(targetEventListener, it) }
      }

    eventListeners.forEach { eventListener ->
      // function befores
      val functionBefores = eventListener.getBefores()
      behaviorClasses
        .filter { targetBehaviorClass -> functionBefores.any { it.accepts(targetBehaviorClass) } }
        .flatMap { it.getEventListeners() }
        .forEach { targetEventListener ->
          graph.addEdge(eventListener, targetEventListener)
        }
      
      // function afters
      val functionAfters = eventListener.getAfters()
      behaviorClasses
        .filter { targetBehaviorClass -> functionAfters.any { it.accepts(targetBehaviorClass) } }
        .flatMap { it.getEventListeners() }
        .forEach { targetEventListener ->
          graph.addEdge(targetEventListener, eventListener)
        }
    }
  }

  return graph.getTopologicalSort()
}