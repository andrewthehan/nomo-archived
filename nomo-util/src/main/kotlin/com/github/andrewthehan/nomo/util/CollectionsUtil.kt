package com.github.andrewthehan.nomo.util

inline fun <reified Actual> Iterable<*>.filterAs()
  = filter { it is Actual }.map { it as Actual }

inline fun <reified Actual> Iterable<*>.singleAs()
  = single { it is Actual } as Actual