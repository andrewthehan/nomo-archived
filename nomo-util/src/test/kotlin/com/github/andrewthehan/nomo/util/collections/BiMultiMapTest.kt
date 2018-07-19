package com.github.andrewthehan.nomo.util.collections

import kotlin.test.Test
import kotlin.test.assertEquals

class BiMultiMapTest {
  @Test
  fun testBiMultiMap() {
    val map = BiMultiMap<Int, String>()
    
    map.put(1, "Foo")
    assertEquals(setOf("Foo"), map[1])
    assertEquals(setOf(1), map.reverse["Foo"])

    map.put(1, "Bar")
    assertEquals(setOf("Foo", "Bar"), map[1])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(setOf(1), map.reverse["Bar"])

    map.put(2, "Baz")
    assertEquals(setOf("Foo", "Bar"), map[1])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(setOf(1), map.reverse["Bar"])
    assertEquals(setOf("Baz"), map[2])
    assertEquals(setOf(2), map.reverse["Baz"])

    map.put(2, "Bar")
    assertEquals(setOf("Foo", "Bar"), map[1])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(setOf("Baz", "Bar"), map[2])
    assertEquals(setOf(2), map.reverse["Baz"])
    assertEquals(setOf(1, 2), map.reverse["Bar"])

    map.remove(1, "Bar")
    assertEquals(setOf("Foo"), map[1])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(setOf("Baz", "Bar"), map[2])
    assertEquals(setOf(2), map.reverse["Baz"])
    assertEquals(setOf(2), map.reverse["Bar"])

    map.removeKey(2)
    assertEquals(setOf("Foo"), map[1])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(emptySet<String>(), map[2])
    assertEquals(emptySet<Int>(), map.reverse["Bar"])
    assertEquals(emptySet<Int>(), map.reverse["Baz"])
  }

  @Test
  fun testBiMultiMap_removeValue() {
    val map = BiMultiMap<Int, String>()

    map.put(1, "Foo")
    map.put(1, "Bar")
    map.put(2, "Baz")
    map.put(2, "Bar")

    assertEquals(setOf("Foo", "Bar"), map[1])
    assertEquals(setOf("Baz", "Bar"), map[2])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(setOf(2), map.reverse["Baz"])
    assertEquals(setOf(1, 2), map.reverse["Bar"])

    map.removeValue("Bar")
    assertEquals(setOf("Foo"), map[1])
    assertEquals(setOf("Baz"), map[2])
    assertEquals(setOf(1), map.reverse["Foo"])
    assertEquals(setOf(2), map.reverse["Baz"])
    assertEquals(emptySet<Int>(), map.reverse["Bar"])
  }

  @Test
  fun testBiMultiMap_reverse() {
    val map = BiMultiMap<String, Int>()
    assertEquals(map, map.reverse.reverse)
  }
}