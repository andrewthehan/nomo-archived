package com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.combat.ecs.components.attributes.HealthAttribute
import com.github.andrewthehan.nomo.boot.combat.ecs.events.DamageEvent
import com.github.andrewthehan.nomo.boot.combat.ecs.events.DeathEvent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Dependent
import com.github.andrewthehan.nomo.sdk.ecs.annotations.EventListener
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.managers.EventManager

@Dependent(HealthAttribute::class)
class DamageableBehavior : AbstractBehavior() {
  @MutableInject
  lateinit var eventManager: EventManager

  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  @EventListener
  fun onDamage(event: DamageEvent) {
    entityComponentManager
      .getEntities(this)
      .forEach {
        val healthAttribute = entityComponentManager.getComponent<HealthAttribute>(it)
        healthAttribute.damage(event.amount)
        if (healthAttribute.isDead()) {
          eventManager.dispatchEvent(DeathEvent(it), it)
        }
      }
  }
}