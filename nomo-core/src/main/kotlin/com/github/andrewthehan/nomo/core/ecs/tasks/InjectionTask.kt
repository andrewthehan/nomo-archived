package com.github.andrewthehan.nomo.core.ecs.tasks

import com.github.andrewthehan.nomo.core.ecs.annotations.Before
import com.github.andrewthehan.nomo.core.ecs.annotations.Befores
import com.github.andrewthehan.nomo.core.ecs.managers.SystemsManager
import com.github.andrewthehan.nomo.core.ecs.tasks.SystemsUpdateTask
import com.github.andrewthehan.nomo.core.ecs.types.EcsObject
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.types.Task
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.core.ecs.util.getInjectableProperties

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

@Befores(
  Before(include = [ SystemsUpdateTask::class ])
)
class InjectionTask(override val ecsEngine: EcsEngine) : Task {
  private val systemsManager by lazy { ecsEngine.managers.get<SystemsManager>()!! }

  fun injectManagers(ecsObject: EcsObject) {
    ecsObject::class
      .getInjectableProperties()
      .filter { it.returnType.jvmErasure.isSubclassOf(Manager::class) }
      .forEach {
        @Suppress("Unchecked_cast")
        val manager = ecsEngine.managers.get(it.returnType.jvmErasure as KClass<out Manager>)!!
        it.setter.call(ecsObject, manager)
      }
  }

  override fun update(delta: Float) {
    systemsManager.systems.forEach {
      injectManagers(it)
    }
  }
}
