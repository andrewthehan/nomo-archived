package com.github.andrewthehan.nomo.sdk.ecs.components.attributes

import com.github.andrewthehan.nomo.util.*

class HealthAttribute<ValueType : Number>(initialHealth: ValueType) : NumberAttribute<ValueType>(initialHealth) {
  fun isAlive(): Boolean = this.value > 0
  fun isDead(): Boolean = this.value == 0

  fun damage(amount: ValueType) {
    this.value -= amount
  }

  fun heal(amount: ValueType) {
    this.value += amount
  }
}