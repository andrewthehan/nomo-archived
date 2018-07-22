package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import com.github.andrewthehan.nomo.core.ecs.annotations.Exclusive
import com.github.andrewthehan.nomo.util.*

@Exclusive
class HealthAttribute<NumberType : Number>(initialHealth: NumberType) : NumberAttribute<NumberType>(initialHealth) {
  fun isAlive(): Boolean = this.value.isPositive()
  fun isDead(): Boolean = this.value.isZero()

  @Suppress("Unchecked_cast")
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