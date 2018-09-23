package com.github.andrewthehan.nomo.sdk.ecs.exceptions

class MissingDependencyException(t: Any, dependencies: Iterable<Any>) : Exception("${t} is missing ${dependencies}")