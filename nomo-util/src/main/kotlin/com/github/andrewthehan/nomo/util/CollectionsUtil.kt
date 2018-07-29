package com.github.andrewthehan.nomo.util

inline fun <reified Actual> Set<*>.filterAs()
  = filter { it is Actual }.map { it as Actual }.toSet()

inline fun <reified Actual> Set<*>.findAs()
  = find { it is Actual } as Actual