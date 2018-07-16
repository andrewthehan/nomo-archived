package com.github.andrewthehan.nomo.util.collections

class BiMultiMap<K, V> : MultiMap<K, V>() {
  val reverse = MultiMap<V, K>()

  override fun put(key: K, value: V) {
    super.put(key, value)
    reverse.put(value, key)
  }

  override fun remove(key: K): MutableSet<V>? {
    val values = super.remove(key)
    values?.forEach { reverse.remove(it, key) }
    return values
  }

  override fun remove(key: K, value: V) = super.remove(key, value) && reverse.remove(value, key)
}