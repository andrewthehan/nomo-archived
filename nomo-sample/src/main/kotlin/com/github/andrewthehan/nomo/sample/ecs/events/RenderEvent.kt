package com.github.andrewthehan.nomo.sample.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Event

import com.badlogic.gdx.graphics.g2d.SpriteBatch

data class RenderEvent(val batch: SpriteBatch) : Event