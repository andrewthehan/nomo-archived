package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import com.github.andrewthehan.nomo.core.ecs.interfaces.Exclusive
import com.github.andrewthehan.nomo.util.*

@Exclusive
class HealthAttribute<ValueType : Number>(initialHealth: ValueType) : NumberAttribute<ValueType>(initialHealth) {  
  fun isAlive(): Boolean = this.value.isPositive()
  fun isDead(): Boolean = this.value.isZero()

  @Suppress("Unchecked_cast")
  fun damage(amount: ValueType) {
    if (amount.isNegative()) {
      throw IllegalArgumentException("Damage amount should be nonnegative: ${amount}")
    }

    this.value -= amount

    if (this.value.isNegative()) {
      this.value = 0 as ValueType
    }
  }

  fun heal(amount: ValueType) {
    if (amount.isNegative()) {
      throw IllegalArgumentException("Heal amount should be nonnegative: ${amount}")
    }

    this.value += amount
  }
}