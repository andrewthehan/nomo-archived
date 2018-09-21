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

  val behaviorClasses = behaviors.map { it::class }

  // create metadata for each behavior's class/method befores/afters
  val behaviorToClassBeforesMap = behaviorClasses.associateBy({ it }, { behaviorClass -> 
    val befores = behaviorClass.getBefores()
    befores
      .map { beforeType -> 
        val include = beforeType.include
        val exclude = beforeType.exclude
        behaviorClasses.filter { behaviorClass ->
          include.any { behaviorClass.isSubclassOf(it) } && exclude.none { behaviorClass.isSubclassOf(it) }
        }
      }
      .flatten()
      .distinct()
      .map { it.getEventListeners() }
      .flatten()
      .map { eventListenerToInfoMap.get(it)!! }
  })
  val behaviorToClassAftersMap = behaviorClasses.associateBy({ it }, { behaviorClass ->
    val afters = behaviorClass.getAfters()
    afters
      .map { afterType -> 
        val include = afterType.include
        val exclude = afterType.exclude
        behaviorClasses.filter { behaviorClass ->
          include.any { behaviorClass.isSubclassOf(it) } && exclude.none { behaviorClass.isSubclassOf(it) }
        }
      }
      .flatten()
      .distinct()
      .map { it.getEventListeners() }
      .flatten()
      .map { eventListenerToInfoMap.get(it)!! }
  })

  // create directed edges
  behaviorClasses.forEach { behaviorClass ->
    val eventListeners = behaviorClass.getEventListeners()
    val eventListenerInfos = eventListeners.map { eventListenerToInfoMap.get(it)!! }

    // class befores
    behaviorToClassBeforesMap.get(behaviorClass)!!.forEach { targetEventListenerInfo ->
      eventListenerInfos.forEach { graph.addEdge(it, targetEventListenerInfo) }
    }

    // class afters
    behaviorToClassAftersMap.get(behaviorClass)!!.forEach { targetEventListenerInfo ->
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
          behaviorClasses.filter { behaviorClass ->
            include.any { behaviorClass.isSubclassOf(it) } && exclude.none { behaviorClass.isSubclassOf(it) }
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
          behaviorClasses.filter { behaviorClass ->
            include.any { behaviorClass.isSubclassOf(it) } && exclude.none { behaviorClass.isSubclassOf(it) }
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