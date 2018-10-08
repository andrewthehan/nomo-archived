package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.*
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.boot.physics.ecs.systems.*
import com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.boot.io.ecs.events.*
import com.github.andrewthehan.nomo.boot.io.*
import com.github.andrewthehan.nomo.boot.util.ecs.events.*
import com.github.andrewthehan.nomo.boot.util.ecs.systems.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sample.ecs.entities.*
import com.github.andrewthehan.nomo.sample.ecs.systems.*
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.*
import com.github.andrewthehan.nomo.sdk.ecs.managers.*
import com.github.andrewthehan.nomo.sdk.ecs.tasks.*
import com.github.andrewthehan.nomo.sdk.ecs.util.*
import com.github.andrewthehan.nomo.util.collections.*
import com.github.andrewthehan.nomo.util.math.*

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx

import com.badlogic.gdx.graphics.Texture;
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.annotations.*

class EcsEngine() : Engine {
  override val managers = TypedSet<Manager>()
  override val tasks = TypedSet<Task>()

  override fun update(delta: Float) {
    val taskClasses = tasks.map { it::class }
    val orderedTaskClasses = getOrder(taskClasses)
    orderedTaskClasses
      .map { tasks.get(it)!! }
      .forEach { it.update(delta) }
  }
}

class Application : ApplicationAdapter() {
  lateinit var engine: Engine
  var i = 0

  override fun create() {
    engine = EcsEngine().apply {
      val ecs = this
      managers.apply {
        add(EntityComponentManager(ecs))
        add(EventManager(ecs))
        add(SystemsManager(ecs))
      }
      tasks.apply {
        add(InjectionTask(ecs))
        add(SystemsUpdateTask(ecs))
        add(DependencyValidatorTask(ecs))
        add(EventPropagationTask(ecs))
      }
    }

    val systemsManager = engine.managers.get<SystemsManager>()!!
    systemsManager.systems.apply {
      add(UpdateSystem())
      add(RenderSystem())
      add(PhysicsStepSystem())
      add(KeyIoSystem())
    }

    create(engine, 3f, 15f)
    val player = create(engine)

    val entityComponentManager = engine.managers.get<EntityComponentManager>()!!

    val components = arrayOf(
      Position2dAttribute(),
      Velocity2dAttribute(),
      Shape2fAttribute(listOf(Vector2f(-1f, -1f), Vector2f(-1f, 1f), Vector2f(1f, 1f), Vector2f(1f, -1f)).map { it * 30f }),
      ShapeRenderBehavior(Color(0f, .7f, .7f, 1f)),
      FollowBehavior(player, 300f)
    )

    entityComponentManager.add("enemy", components)
  }

  override fun render() {
    val entityComponentManager = engine.managers.get<EntityComponentManager>()!!
    val c = entityComponentManager.getComponents<KeyPressActionBehavior>().single()
    val entity = entityComponentManager.getEntities(c).single()
    val position = entityComponentManager.getComponent<Position2dAttribute>(entity)
    if (position.y < 0f) {
      Gdx.app.exit()
    }
    println("******** LOOP ${++i} ********")
    engine.update(Gdx.graphics.getDeltaTime())
  }
}

fun main(args: Array<String>) {
  val config = LwjglApplicationConfiguration()
  with (config) {
    title = "nomo"
    width = 1366
    height = 768
  }
  LwjglApplication(Application(), config)
}