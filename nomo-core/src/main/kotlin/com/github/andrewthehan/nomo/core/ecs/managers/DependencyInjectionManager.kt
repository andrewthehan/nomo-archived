package com.github.andrewthehan.nomo.core.ecs.managers

import com.github.andrewthehan.nomo.core.ecs.types.EcsObject
import com.github.andrewthehan.nomo.core.ecs.types.Manager
import com.github.andrewthehan.nomo.core.ecs.EcsEngine
import com.github.andrewthehan.nomo.core.ecs.util.getInjectableManagerProperties

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

class DependencyInjectionManager(override val ecsEngine: EcsEngine) : Manager {
  fun injectManagers(ecsObject: EcsObject) {
    getInjectableManagerProperties(ecsObject::class)
      .filter { it.returnType.jvmErasure.isSubclassOf(Manager::class) }
      .forEach {
        @Suppress("Unchecked_cast")
        val manager = ecsEngine.getManager(it.returnType.jvmErasure as KClass<out Manager>)!!
        it.setter.call(ecsObject, manager)
      }
  }
}
