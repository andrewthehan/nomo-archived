package com.github.andrewthehan.nomo.boot.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.NumberAttribute
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.util.*

class HealthAttribute<NumberType : Number>(initialHealth: NumberType) : NumberAttribute<NumberType>(initialHealth), Exclusive {
  fun isAlive(): Boolean = this.value.isPositive()
  fun isDead(): Boolean = this.value.isZero()

  @Suppress("UNCHECKED_CAST")
  fun damage(amount: NumberType) {
    if (amount.isNegative()) {
      throw IllegalArgumentException("Damage amount should be nonnegative: ${amount}")
    }

    this.value -= amount

    if (this.value.isNegative()) {
      this.value = 0 as NumberType
    }
  }

  fun heal(amount: NumberType) {
    if (amount.isNegative()) {
      throw IllegalArgumentException("Heal amount should be nonnegative: ${amount}")
    }

    this.value += amount
  }
}