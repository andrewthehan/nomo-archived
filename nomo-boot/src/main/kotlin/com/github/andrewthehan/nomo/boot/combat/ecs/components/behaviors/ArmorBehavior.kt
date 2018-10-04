package com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.combat.ecs.events.DamageEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Before
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Befores
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior

@Dependent(DamageableBehavior::class)
class ArmorBehavior(val reduction: Float) : AbstractBehavior() {
  @EventListener
  @Befores(
    Before(include = [ DamageableBehavior::class ])
  )
  fun applyArmor(event: DamageEvent) {
    event.amount *= reduction
  }
}