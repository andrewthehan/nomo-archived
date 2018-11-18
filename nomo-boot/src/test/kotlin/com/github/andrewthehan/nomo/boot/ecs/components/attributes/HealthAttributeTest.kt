package com.github.andrewthehan.nomo.boot.combat.ecs.components.attributes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HealthAttributeTest {
  @Test
  fun testHealthAttribute_holistic() {
    val healthAttribute = HealthAttribute(100f)
    assertEquals(100f, healthAttribute.health)

    healthAttribute.damage(25f)
    assertEquals(75f, healthAttribute.health)

    healthAttribute.heal(50f)
    assertEquals(125f, healthAttribute.health)

    assertTrue(healthAttribute.isAlive())

    healthAttribute.damage(125f)
    assertTrue(healthAttribute.isDead())
  }

  @Test(expected = IllegalArgumentException::class)
  fun testHealthAttribute_negativeHeal() {
    val healthAttribute = HealthAttribute(100f)
    healthAttribute.heal(-25f)
  }

  @Test(expected = IllegalArgumentException::class)
  fun testHealthAttribute_negativeHealFloat() {
    val healthAttribute = HealthAttribute(100f)
    healthAttribute.heal(-25f)
  }
}