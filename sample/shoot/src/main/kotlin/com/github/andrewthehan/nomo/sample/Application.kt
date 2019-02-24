package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.boot.collision.ecs.events.*
import com.github.andrewthehan.nomo.boot.collision.ecs.systems.*
import com.github.andrewthehan.nomo.boot.collision.detectors.*
import com.github.andrewthehan.nomo.boot.combat.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.*
import com.github.andrewthehan.nomo.boot.physics.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.boot.physics.ecs.systems.*
import com.github.andrewthehan.nomo.boot.io.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.boot.io.ecs.events.*
import com.github.andrewthehan.nomo.boot.io.*
import com.github.andrewthehan.nomo.boot.time.ecs.events.*
import com.github.andrewthehan.nomo.boot.time.ecs.systems.*
import com.github.andrewthehan.nomo.boot.time.util.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.sample.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sample.ecs.entities.*
import com.github.andrewthehan.nomo.sample.ecs.systems.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.annotations.*
import com.github.andrewthehan.nomo.sdk.ecs.interfaces.*
import com.github.andrewthehan.nomo.sdk.ecs.managers.*
import com.github.andrewthehan.nomo.sdk.ecs.tasks.*
import com.github.andrewthehan.nomo.sdk.ecs.util.*
import com.github.andrewthehan.nomo.util.collections.*
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.*

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx

import com.badlogic.gdx.graphics.Texture;

import kotlin.math.*

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
      add(Physics2dStepSystem())
      add(KeyIoSystem())
      add(MouseIoSystem())
      add(CollisionDetectionSystem().apply {
        addCollisionDetector(LayerCollisionDetector())
        addCollisionDetector(BoundingBoxCollisionDetector())
      })
      add(EnemySpawnSystem())
    }

    createCamera(engine, "player")
    createFps(engine, 3f, 15f, "fps")
    createPlayer(engine, "player")
  }

  override fun render() {
    // no players remaining
    val playerAttributes = engine.managers.get<EntityComponentManager>()!!.getComponents<PlayerAttribute>()
    if (playerAttributes.none()) {
      Gdx.app.exit()
    }

    engine.update(Gdx.graphics.getDeltaTime())
  }
}

fun main(args: Array<String>) {
  val config = LwjglApplicationConfiguration()
  with (config) {
    title = "nomo"
    width = 1366
    height = 768
    forceExit = false
  }
  LwjglApplication(Application(), config)
}