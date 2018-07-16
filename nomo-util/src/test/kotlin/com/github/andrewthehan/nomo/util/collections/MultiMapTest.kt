package com.github.andrewthehan.nomo.util.collections

import kotlin.test.Test
import kotlin.test.assertEquals

class MultiMapTest {
  @Test
  fun testMultiMap() {
    val map = MultiMap<Int, String>()
    
    map.put(1, "Foo")
    assertEquals(setOf("Foo"), map[1])

    map.put(1, "Bar")
    assertEquals(setOf("Foo", "Bar"), map[1])

    map.put(2, "Baz")
    assertEquals(setOf("Foo", "Bar"), map[1])
    assertEquals(setOf("Baz"), map[2])

    map.put(2, "Bar")
    assertEquals(setOf("Foo", "Bar"), map[1])
    assertEquals(setOf("Baz", "Bar"), map[2])

    map.remove(1, "Bar")
    assertEquals(setOf("Foo"), map[1])
    assertEquals(setOf("Baz", "Bar"), map[2])

    map.remove(2)
    assertEquals(setOf("Foo"), map[1])
    assertEquals(emptySet<String>(), map[2])
  }
}