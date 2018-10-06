package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes.*
import com.github.andrewthehan.nomo.boot.physics.ecs.systems.*
import com.github.andrewthehan.nomo.boot.io.ecs.events.*
import com.github.andrewthehan.nomo.boot.io.*
import com.github.andrewthehan.nomo.boot.util.ecs.events.*
import com.github.andrewthehan.nomo.boot.util.ecs.systems.*
import com.github.andrewthehan.nomo.core.ecs.types.*
import com.github.andrewthehan.nomo.sample.ecs.entities.*
import com.github.andrewthehan.nomo.sample.ecs.systems.*
import com.github.andrewthehan.nomo.sdk.ecs.managers.*
import com.github.andrewthehan.nomo.sdk.ecs.tasks.*
import com.github.andrewthehan.nomo.sdk.ecs.util.*
import com.github.andrewthehan.nomo.util.collections.*
import com.github.andrewthehan.nomo.util.math.*

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx

import ktx.app.KtxApplicationAdapter

import com.badlogic.gdx.graphics.Texture;
// import com.github.andrewthehan.nomo.sample.ecs.components.attributes.*
import com.github.andrewthehan.nomo.sample.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.events.*
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.*
import com.github.andrewthehan.nomo.sdk.ecs.annotations.*

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

class Application : KtxApplicationAdapter {
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
    
    val components = arrayOf(
      Position2dAttribute(100f, 100f),
      Velocity2dAttribute(),
      Acceleration2dAttribute(),
      ImageRenderBehavior(Texture(Gdx.files.internal("image.png"))),
      object : AbstractBehavior() {
        @MutableInject
        lateinit var entityComponentManager: EntityComponentManager

        private val deacceleration = -1000f

        @EventListener
        fun slowDown(event: UpdateEvent) {
          val entities = entityComponentManager.getEntities(this)
          entities
            .map { entityComponentManager.getComponent<Velocity2dAttribute>(it) }
            .filter { !it.isZero() }
            .forEach {
              val delta = Vector2f(sign(it.x), sign(it.y)) * deacceleration * event.delta
              it.x =
                if (abs(it.x) < abs(delta.x)) { 0f }
                else { it.x + delta.x }
              it.y =
                if (abs(it.y) < abs(delta.y)) { 0f }
                else { it.y + delta.y }
            }
        }
      },
      object : AbstractBehavior() {
        @MutableInject
        lateinit var entityComponentManager: EntityComponentManager

        private val relevantKeys = setOf(Key.W, Key.A, Key.S, Key.D)
        private val speed = 1500f

        @EventListener
        fun move(event: KeyPressedEvent) {
          if (!relevantKeys.contains(event.key)) {
            return
          }
          
          val entities = entityComponentManager.getEntities(this)
          entities
            .map { entityComponentManager.getComponent<Acceleration2dAttribute>(it) }
            .forEach {
              when(event.key) {
                Key.D -> it.x += speed
                Key.A -> it.x -= speed
                Key.W -> it.y += speed
                Key.S -> it.y -= speed
                else -> throw AssertionError()
              }
            }
        }

        @EventListener
        fun move(event: KeyReleasedEvent) {
          if (!relevantKeys.contains(event.key)) {
            return
          }
          
          val entities = entityComponentManager.getEntities(this)
          entities
            .map { entityComponentManager.getComponent<Acceleration2dAttribute>(it) }
            .forEach {
              when(event.key) {
                Key.D -> it.x -= speed
                Key.A -> it.x += speed
                Key.W -> it.y -= speed
                Key.S -> it.y += speed
                else -> throw AssertionError()
              }
            }
        }
      }
    )
    engine.managers.get<EntityComponentManager>()!!.add("face", components)
  }

  override fun render() {
    val entityComponentManager = engine.managers.get<EntityComponentManager>()!!
    val velocity = entityComponentManager.getComponents<Velocity2dAttribute>().single()
    val entity = entityComponentManager.getEntities(velocity).single()
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