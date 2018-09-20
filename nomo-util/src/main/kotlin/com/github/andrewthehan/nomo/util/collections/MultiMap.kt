package com.github.andrewthehan.nomo.util.collections

open class MultiMap<K, V> : Cloneable {
  private val map = HashMap<K, MutableSet<V>>()
  val entries = map.entries
  val keys = map.keys
  val size = map.size
  val values = map.values

  override public fun clone(): Any {
    val clone = MultiMap<K, V>()
    entries.forEach { (key, value) -> value.forEach { clone.put(key, it) } }
    return clone
  }

  fun containsKey(key: K): Boolean = map.containsKey(key)
  fun containsValue(value: V): Boolean = map.values.any { it.contains(value) }

  operator fun get(key: K): MutableSet<V> = map[key] ?: mutableSetOf()

  open fun put(key: K, value: V) {
    val values = get(key)
    values.add(value)
    map[key] = values
  }

  open fun remove(key: K) = map.remove(key)

  open fun remove(key: K, value: V): Boolean {
    val values = get(key)
    val toReturn = values.remove(value)
    if (values.isEmpty()) {
      map.remove(key)
    }
    return toReturn
  }
}