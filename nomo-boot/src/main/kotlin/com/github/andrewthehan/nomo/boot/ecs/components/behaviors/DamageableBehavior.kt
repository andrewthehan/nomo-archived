package com.github.andrewthehan.nomo.boot.ecs.components.behaviors

import com.github.andrewthehan.nomo.boot.ecs.components.attributes.HealthAttribute
import com.github.andrewthehan.nomo.boot.ecs.events.DamageEvent
import com.github.andrewthehan.nomo.boot.ecs.events.DeathEvent
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

  @EventListener(DamageEvent::class)
  fun <NumberType : Number> onDamage(event: DamageEvent<NumberType>) {
    entityComponentManager
      .getEntities(this)
      .forEach {
        val healthAttribute = entityComponentManager.getComponent<HealthAttribute<NumberType>>(it)
        healthAttribute.damage(event.amount)
        if (healthAttribute.isDead()) {
          eventManager.dispatchEvent(DeathEvent(it), it)
        }
      }
  }
}