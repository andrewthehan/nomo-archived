package com.github.andrewthehan.nomo.boot.combat.ecs.components.attributes

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.MutableVector1fAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

class HealthAttribute(initialHealth: Float) : MutableVector1fAttribute(initialHealth), Exclusive {
  fun isAlive(): Boolean = x > 0f
  fun isDead(): Boolean = x == 0f

  fun damage(amount: Float) {
    if (amount == 0f) {
      return
    }

    require(amount >= 0f) { "Damage amount should be nonnegative: ${amount}" }

    x -= amount

    if (x < 0f) {
      x = 0f
    }
  }

  fun heal(amount: Float) {
    if (amount == 0f) {
      return
    }

    require(amount >= 0f) { "Heal amount should be nonnegative: ${amount}" }

    x += amount
  }
}