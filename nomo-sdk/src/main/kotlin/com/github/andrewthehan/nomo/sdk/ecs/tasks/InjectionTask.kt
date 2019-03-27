package com.github.andrewthehan.nomo.sdk.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.types.EcsObject
import com.github.andrewthehan.nomo.core.ecs.types.Engine
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Before
import com.github.andrewthehan.nomo.sdk.ecs.annotations.Befores
import com.github.andrewthehan.nomo.sdk.ecs.managers.SystemsManager
import com.github.andrewthehan.nomo.sdk.ecs.util.getInjectableProperties

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

@Befores(
  Before(include = [ SystemsUpdateTask::class ])
)
class InjectionTask(override val engine: Engine) : Task {
  private val systemsManager by lazy { engine.managers.get<SystemsManager>()!! }

  fun injectManagers(ecsObject: EcsObject) {
    ecsObject::class
      .getInjectableProperties()
      .filter { it.returnType.jvmErasure.isSubclassOf(Manager::class) }
      .forEach {
        // cast success is guaranteed due to filter above
        @Suppress("UNCHECKED_CAST")
        val manager = engine.managers.get(it.returnType.jvmErasure as KClass<out Manager>)!!
        it.setter.call(ecsObject, manager)
      }
  }

  override fun update(delta: Float) {
    systemsManager.systems.forEach {
      injectManagers(it)
    }
  }
}
