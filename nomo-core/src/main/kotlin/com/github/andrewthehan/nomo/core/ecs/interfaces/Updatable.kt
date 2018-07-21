package com.github.andrewthehan.nomo.core.ecs.interfaces

interface Updatable<NumberType: Number> {
  fun update(delta: NumberType)
}