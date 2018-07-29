package com.github.andrewthehan.nomo.util.collections

inline fun <reified Actual> findAs(set: MutableSet<*>)
  = set.filter { it is Actual }.map { it as Actual }.toMutableSet()

class TypedBiMultiMap<K, V> : BiMultiMap<K, V>() {
  inline fun <reified Actual: V> getValues(key: K) = findAs<Actual>(this[key])

  inline fun <reified Actual: K> getKeys(value: V) = findAs<Actual>(this.reverse[value])
}