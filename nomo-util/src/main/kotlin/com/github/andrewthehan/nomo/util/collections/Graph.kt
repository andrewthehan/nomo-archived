package com.github.andrewthehan.nomo.util.collections

class Graph<T> {
  val edges = BiMultiMap<T, T>()
  val nodes = HashSet<T>()

  fun contains(t: T) = nodes.contains(t)

  fun addNode(t: T) = nodes.add(t)

  fun addEdge(t1: T, t2: T) {
    if (!contains(t1) || !contains(t2)) {
      return
    }

    edges.put(t1, t2)
  }

  fun getOutgoingEdges(t: T) = edges[t]

  fun getIncomingEdges(t: T) = edges.reverse[t]

  fun hasOutgoingEdges(t: T) = getOutgoingEdges(t).any()

  fun hasIncomingEdges(t: T) = getIncomingEdges(t).any()

  fun removeEdge(t1: T, t2: T) = nodes.contains(t1) && nodes.contains(t2) && edges.remove(t1, t2)

  fun removeNode(t: T) = nodes.remove(t) && edges.removeKey(t) != null && edges.removeValue(t) != null
}