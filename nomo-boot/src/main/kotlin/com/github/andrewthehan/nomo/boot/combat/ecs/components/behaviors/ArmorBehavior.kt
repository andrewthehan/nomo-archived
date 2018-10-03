package com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.combat.ecs.events.DamageEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Before
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Befores
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.util.times

@Dependent(DamageableBehavior::class)
class ArmorBehavior(val reduction: Float) : AbstractBehavior() {
  @EventListener
  @Befores(
    Before(include = [ DamageableBehavior::class ])
  )
  fun <NumberType : Number> applyArmor(event: DamageEvent<NumberType>) {
    val reduced = event.amount * reduction
    @Suppress("UNCHECKED_CAST")
    event.amount = reduced as NumberType
  }
}