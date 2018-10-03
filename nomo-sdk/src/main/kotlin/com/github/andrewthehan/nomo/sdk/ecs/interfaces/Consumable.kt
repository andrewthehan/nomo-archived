package com.github.andrewthehan.nomo.sdk.ecs.interfaces

interface Consumable {
  var isConsumed: Boolean 

  fun consume() {
    isConsumed = true
  }
}