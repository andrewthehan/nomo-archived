package com.github.andrewthehan.nomo.sample.ecs.components.behaviors

import com.github.andrewthehan.nomo.sample.ecs.events.RenderEvent
import com.github.andrewthehan.nomo.sdk.ecs.components.behaviors.AbstractBehavior

import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class RenderBehavior() : AbstractBehavior() {
  abstract fun render(event: RenderEvent)
}