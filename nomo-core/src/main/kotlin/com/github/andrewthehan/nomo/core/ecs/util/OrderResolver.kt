package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.util.EventListenerInfo
import com.github.andrewthehan.nomo.core.ecs.util.getBefores
import com.github.andrewthehan.nomo.core.ecs.util.getAfters
import com.github.andrewthehan.nomo.util.collections.Graph

import kotlin.reflect.KFunction
import kotlin.reflect.full.isSubclassOf

fun <T : Behavior> getEventListenerOrder(behaviors: Iterable<T>): List<EventListenerInfo> {
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
  behaviors.forEach { behavior ->
    val eventListeners = behavior::class.getEventListeners()
    val eventListenerInfos = eventListeners.map { eventListenerToInfoMap.get(it)!! }

    // class befores
    val befores = behavior::class.getBefores()
    befores
      .map { beforeType -> 
        val include = beforeType.include
        val exclude = beforeType.exclude
        behaviors.map { it::class }.filter { behavior ->
          include.any { behavior.isSubclassOf(it) } && exclude.none { behavior.isSubclassOf(it) }
        }
      }
      .flatten()
      .distinct()
      .map { it.getEventListeners() }
      .flatten()
      .map { eventListenerToInfoMap.get(it)!! }
      .forEach { targetEventListenerInfo ->
        eventListenerInfos.forEach { graph.addEdge(it, targetEventListenerInfo) }
      }

    // class afters
    val afters = behavior::class.getAfters()
    afters
      .map { afterType -> 
        val include = afterType.include
        val exclude = afterType.exclude
        behaviors.map { it::class }.filter { behavior ->
          include.any { behavior.isSubclassOf(it) } && exclude.none { behavior.isSubclassOf(it) }
        }
      }
      .flatten()
      .distinct()
      .map { it.getEventListeners() }
      .flatten()
      .map { eventListenerToInfoMap.get(it)!! }
      .forEach { targetEventListenerInfo ->
        eventListenerInfos.forEach { graph.addEdge(targetEventListenerInfo, it) }
      }

    eventListenerInfos.forEach { eventListenerInfo ->
      val eventListener = eventListenerInfo.eventListener

      // function befores
      val functionBefores = eventListener.getBefores()
      functionBefores
        .map { beforeType -> 
          val include = beforeType.include
          val exclude = beforeType.exclude
          behaviors.map { it::class }.filter { behavior ->
            include.any { behavior.isSubclassOf(it) } && exclude.none { behavior.isSubclassOf(it) }
          }
        }
        .flatten()
        .distinct()
        .map { it.getEventListeners() }
        .flatten()
        .map { eventListenerToInfoMap.get(it)!! }
        .forEach { targetEventListenerInfo ->
          graph.addEdge(eventListenerInfo, targetEventListenerInfo)
        }
      
      // function afters
      val functionAfters = eventListener.getAfters()
      functionAfters
        .map { afterType -> 
          val include = afterType.include
          val exclude = afterType.exclude
          behaviors.map { it::class }.filter { behavior ->
            include.any { behavior.isSubclassOf(it) } && exclude.none { behavior.isSubclassOf(it) }
          }
        }
        .flatten()
        .distinct()
        .map { it.getEventListeners() }
        .flatten()
        .map { eventListenerToInfoMap.get(it)!! }
        .forEach { targetEventListenerInfo ->
          graph.addEdge(targetEventListenerInfo, eventListenerInfo)
        }
    }
  }

  // find topological sort
  return graph.getTopologicalSort()
}