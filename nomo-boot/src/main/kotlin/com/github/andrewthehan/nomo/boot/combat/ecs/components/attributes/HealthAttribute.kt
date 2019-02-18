package com.github.andrewthehan.nomo.boot.combat.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive

import kotlin.math.min
import kotlin.math.max

class HealthAttribute(initialHealth: Float, var maxHealth: Float = Float.MAX_VALUE) : AbstractAttribute(), Exclusive {
  var health: Float = initialHealth

  fun isAlive(): Boolean = health > 0f
  fun isDead(): Boolean = health == 0f

  fun damage(amount: Float) {
    if (amount == 0f) {
      return
    }

    require(amount >= 0f) { "Damage amount should be nonnegative: $amount" }

    health = max(health - amount, 0f)
  }

  fun heal(amount: Float) {
    if (amount == 0f) {
      return
    }

    require(amount >= 0f) { "Heal amount should be nonnegative: $amount" }

    health = min(health + amount, maxHealth)
  }
}