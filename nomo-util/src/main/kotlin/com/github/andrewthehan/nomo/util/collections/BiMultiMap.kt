package com.github.andrewthehan.nomo.util.collections

class BiMultiMap<K, V> {
  private val forwardMap: MultiMap<K, V>
  private val reverseMap: MultiMap<V, K>
  val reverse: BiMultiMap<V, K>

  constructor() {
    forwardMap = MultiMap<K, V>()
    reverseMap = MultiMap<V, K>()
    reverse = BiMultiMap(reverseMap, forwardMap, this)
  }

  private constructor(forwardMap: MultiMap<K, V>, reverseMap: MultiMap<V, K>, reverse: BiMultiMap<V, K>) {
    this.forwardMap = forwardMap
    this.reverseMap = reverseMap
    this.reverse = reverse
  }

  operator fun get(key: K) = forwardMap[key]
  
  fun containsKey(key: K) = forwardMap.containsKey(key)
  fun containsValue(value: V) = reverseMap.containsKey(value)

  fun put(key: K, value: V) {
    forwardMap.put(key, value)
    reverseMap.put(value, key)
  }

  fun removeKey(key: K): MutableSet<V>? {
    val values = forwardMap.remove(key)
    values?.forEach { reverseMap.remove(it, key) }
    return values
  }

  fun removeValue(value: V): MutableSet<K>? {
    val keys = reverseMap.remove(value)
    keys?.forEach { forwardMap.remove(it, value) }
    return keys
  }

  fun remove(key: K, value: V) = forwardMap.remove(key, value) && reverseMap.remove(value, key)
}