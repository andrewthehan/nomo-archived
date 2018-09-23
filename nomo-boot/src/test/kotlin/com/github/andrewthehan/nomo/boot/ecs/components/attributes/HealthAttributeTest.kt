package com.github.andrewthehan.nomo.boot.ecs.components.attributes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HealthAttributeTest {
  @Test
  fun testHealthAttribute_holistic() {
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
  
  @Test
  fun testHealthAttribute_float() {
    val healthAttribute = HealthAttribute(100f)
    assertEquals(100f, healthAttribute.value)

    healthAttribute.damage(12.5f)
    assertEquals(87.5f, healthAttribute.value)

    healthAttribute.heal(2.5f)
    assertEquals(90f, healthAttribute.value)

    assertTrue(healthAttribute.isAlive())

    healthAttribute.damage(90f)
    assertTrue(healthAttribute.isDead())
  }

  @Test(expected = IllegalArgumentException::class)
  fun testHealthAttribute_negativeHeal() {
    val healthAttribute = HealthAttribute(100)
    healthAttribute.heal(-25)
  }

  @Test(expected = IllegalArgumentException::class)
  fun testHealthAttribute_negativeHealFloat() {
    val healthAttribute = HealthAttribute(100f)
    healthAttribute.heal(-25f)
  }
}