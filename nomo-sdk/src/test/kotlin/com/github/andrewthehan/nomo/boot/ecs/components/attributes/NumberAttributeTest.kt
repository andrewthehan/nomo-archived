package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import kotlin.test.Test
import kotlin.test.assertEquals

class NumberAttributeTest {
  @Test
  fun testNumberAttribute() {
    val numberAttribute = NumberAttribute(100)
    assertEquals(100, numberAttribute.value)

    numberAttribute.value += 50
    assertEquals(150, numberAttribute.value)

    numberAttribute.value -= 25
    assertEquals(125, numberAttribute.value)
  }
}