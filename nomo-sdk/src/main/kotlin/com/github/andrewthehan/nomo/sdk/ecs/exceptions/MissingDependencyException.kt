package com.github.andrewthehan.nomo.sdk.ecs.exceptions

class MissingDependencyException(target: Any, dependencies: Iterable<Any>) : Exception("Target (${target}) is missing ${dependencies}")