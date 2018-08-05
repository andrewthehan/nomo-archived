package com.github.andrewthehan.nomo.core.ecs.util

import com.github.andrewthehan.nomo.core.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.core.ecs.types.Behavior
import com.github.andrewthehan.nomo.core.ecs.util.EventListenerInfo
import com.github.andrewthehan.nomo.core.ecs.util.getBefores
import com.github.andrewthehan.nomo.core.ecs.util.getAfters
import com.github.andrewthehan.nomo.util.collections.Graph

import kotlin.reflect.KFunction

fun <T : Behavior> getEventListenerOrder(behaviors: Iterable<T>): Array<EventListenerInfo> {
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
      .map { it.getEventListeners() }
      .flatten()
      .map { eventListenerToInfoMap.get(it)!! }
      .forEach { targetEventListenerInfo ->
        eventListenerInfos.forEach { graph.addEdge(it, targetEventListenerInfo) }
      }

    // class afters
    val afters = behavior::class.getAfters()
    afters
      .map { it.getEventListeners() }
      .flatten()
      .map { eventListenerToInfoMap.get(it)!! }
      .forEach { targetEventListenerInfo ->
        eventListenerInfos.forEach { graph.addEdge(targetEventListenerInfo, it) }
      }

    eventListenerInfos.forEach { eventListenerInfo ->
      val eventListener = eventListenerInfo.eventListener

      // function befores
      val functionBefores = eventListener::class.getBefores()
      functionBefores
        .map { it.getEventListeners() }
        .flatten()
        .map { eventListenerToInfoMap.get(it)!! }
        .forEach { targetEventListenerInfo ->
          graph.addEdge(eventListenerInfo, targetEventListenerInfo)
        }
      
      // function afters
      val functionAfters = eventListener::class.getAfters()
      functionAfters
        .map { it.getEventListeners() }
        .flatten()
        .map { eventListenerToInfoMap.get(it)!! }
        .forEach { targetEventListenerInfo ->
          graph.addEdge(targetEventListenerInfo, eventListenerInfo)
        }
    }
  }

  // find topological sort (kahn's algorithm)
  val sort = mutableListOf<EventListenerInfo>()
  val noIncoming = graph.nodes.filter { !graph.hasIncomingEdges(it) }.toMutableSet()
  while (noIncoming.any()) {
    val node = noIncoming.first()
    noIncoming.remove(node)
    sort.add(node)
    val outgoing = graph.getOutgoingEdges(node)
    graph.removeNode(node)
    noIncoming.addAll(outgoing.filter { !graph.hasIncomingEdges(it) })
  }

  if (graph.nodes.any()) {
    throw Exception("TODO: create custom cycle detected exception")
  }

  return sort.toTypedArray()
}