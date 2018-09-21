package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.accepts
import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.util.EventListenerInfo
import com.github.andrewthehan.nomo.core.ecs.util.getBefores
import com.github.andrewthehan.nomo.core.ecs.util.getAfters
import com.github.andrewthehan.nomo.util.collections.Graph

import kotlin.reflect.KFunction

fun <T : Behavior> getEventListenerOrder(behaviors: Iterable<T>): List<EventListenerInfo> {
  val behaviorClasses = behaviors.map { it::class }

  // create metadata for each behavior's class's befores/afters
  val behaviorClassToBeforeEventListenersMap = behaviorClasses.associateBy({ it }, { behaviorClass ->
    val classBefores = behaviorClass.getBefores()
    behaviorClasses
      .filter { targetBehaviorClass -> classBefores.any { it.accepts(targetBehaviorClass) } }
      .flatMap { it.getEventListeners() }
  })
  val behaviorClassToAfterEventListenersMap = behaviorClasses.associateBy({ it }, { behaviorClass ->
    val classAfters = behaviorClass.getAfters()
    behaviorClasses
      .filter { targetBehaviorClass -> classAfters.any { it.accepts(targetBehaviorClass) } }
      .flatMap { it.getEventListeners() }
  })

  val graph = Graph<EventListenerInfo>()
  val eventListenerToInfoMap = HashMap<KFunction<*>, EventListenerInfo>()

  // create event listener nodes
  behaviors.forEach { behavior ->
    val eventListeners = behavior::class.getEventListeners()
    eventListeners
      .map { EventListenerInfo(behavior, it) }
      .forEach {
        graph.addNode(it)
        eventListenerToInfoMap.put(it.eventListener, it)
      }
  }

  // create directed edges
  behaviorClasses.forEach { behaviorClass ->
    val eventListeners = behaviorClass.getEventListeners()
    val eventListenerInfos = eventListeners.map { eventListenerToInfoMap.get(it)!! }

    // class befores
    behaviorClassToBeforeEventListenersMap
      .get(behaviorClass)!!
      .map { eventListenerToInfoMap.get(it)!! }
      .forEach { targetEventListenerInfo ->
        eventListenerInfos.forEach { graph.addEdge(it, targetEventListenerInfo) }
      }

    // class afters
    behaviorClassToAfterEventListenersMap
      .get(behaviorClass)!!
      .map { eventListenerToInfoMap.get(it)!! }
      .forEach { targetEventListenerInfo ->
        eventListenerInfos.forEach { graph.addEdge(targetEventListenerInfo, it) }
      }

    eventListenerInfos.forEach { eventListenerInfo ->
      val eventListener = eventListenerInfo.eventListener

      // function befores
      val functionBefores = eventListener.getBefores()
      behaviorClasses
        .filter { targetBehaviorClass -> functionBefores.any { it.accepts(targetBehaviorClass) } }
        .flatMap { it.getEventListeners() }
        .map { eventListenerToInfoMap.get(it)!! }
        .forEach { targetEventListenerInfo ->
          graph.addEdge(eventListenerInfo, targetEventListenerInfo)
        }
      
      // function afters
      val functionAfters = eventListener.getAfters()
      behaviorClasses
        .filter { targetBehaviorClass -> functionAfters.any { it.accepts(targetBehaviorClass) } }
        .flatMap { it.getEventListeners() }
        .map { eventListenerToInfoMap.get(it)!! }
        .forEach { targetEventListenerInfo ->
          graph.addEdge(targetEventListenerInfo, eventListenerInfo)
        }
    }
  }

  // find topological sort
  return graph.getTopologicalSort()
}