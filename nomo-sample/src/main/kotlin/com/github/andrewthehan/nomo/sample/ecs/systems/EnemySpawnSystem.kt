package com.github.andrewthehan.nomo.sample.ecs.systems

import com.github.andrewthehan.nomo.boot.time.util.Timer
import com.github.andrewthehan.nomo.sample.ecs.entities.createEnemy
import com.github.andrewthehan.nomo.sdk.ecs.annotations.MutableInject
import com.github.andrewthehan.nomo.sdk.ecs.managers.EntityComponentManager
import com.github.andrewthehan.nomo.sdk.ecs.systems.AbstractSystem

class EnemySpawnSystem : AbstractSystem {
  @MutableInject
  lateinit var entityComponentManager: EntityComponentManager

  private val timer = Timer(0.5f)

  constructor() : super(){
  }

  override fun update(delta: Float) {
    timer.update(delta)
    while (timer.shouldTrigger()) {
      createEnemy(entityComponentManager.engine)
    }
  }
}