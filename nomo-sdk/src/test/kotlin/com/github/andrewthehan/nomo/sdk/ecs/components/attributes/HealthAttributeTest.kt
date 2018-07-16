package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HealthAttributeTest {
  @Test
  fun testHealthAttribute() {
    val healthAttribute = HealthAttribute(100)
    assertEquals(100, healthAttribute.value)

    healthAttribute.damage(25)
    assertEquals(75, healthAttribute.value)

    healthAttribute.heal(50)
    assertEquals(125, healthAttribute.value)

    assertTrue(healthAttribute.isAlive())

    healthAttribute.damage(125)
    assertTrue(healthAttribute.isDead())
  }
}