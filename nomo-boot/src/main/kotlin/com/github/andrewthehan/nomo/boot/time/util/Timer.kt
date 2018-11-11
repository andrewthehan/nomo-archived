package com.github.andrewthehan.nomo.boot.time.util

class Timer(val updateDelay: Float) {
  private var timeElapsedSinceUpdate: Float = 0f

  fun update(delta: Float) {
    timeElapsedSinceUpdate += delta
  }

  fun shouldTrigger(): Boolean {
    if (timeElapsedSinceUpdate > updateDelay) {
      timeElapsedSinceUpdate -= updateDelay
      return true
    }

    return false
  }
}