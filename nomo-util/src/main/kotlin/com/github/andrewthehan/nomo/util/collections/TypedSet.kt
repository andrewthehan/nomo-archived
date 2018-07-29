package com.github.andrewthehan.nomo.util.collections

import kotlin.reflect.KClass

class TypedSet<T: Any> : MutableSet<T> {
  val map = HashMap<KClass<*>, T>()

  override val size = map.size

  override fun add(element: T): Boolean {
    if (contains(element::class)) {
      return false
    }
    map.put(element::class, element)
    return true
  }

  override fun addAll(elements: Collection<T>) = elements.any { add(it) }

  override fun clear() = map.clear()

  override fun iterator() = map.values.iterator()

  override fun remove(element: T) = map.remove(element::class) != null

  fun <Actual : T> remove(tClass: KClass<Actual>) = map.remove(tClass)

  inline fun <reified Actual : T> remove() = map.remove(Actual::class)

  override fun removeAll(elements: Collection<T>) = elements.any { remove(it) }

  override fun retainAll(elements: Collection<T>) = filter { !elements.contains(it) }.any { remove(it) }

  override fun contains(element: T) = map.containsKey(element::class)

  fun <Actual : T> contains(tClass: KClass<Actual>) = map.containsKey(tClass)

  inline fun <reified Actual : T> contains() = map.containsKey(Actual::class)

  override fun containsAll(elements: Collection<T>) = map.values.containsAll(elements)

  override fun isEmpty() = map.isEmpty()

  inline fun <reified Actual : T> get() = map.get(Actual::class) as? Actual

  @Suppress("Unchecked_cast")
  fun <Actual : T> get(tClass: KClass<Actual>) = map.get(tClass) as? Actual
}
