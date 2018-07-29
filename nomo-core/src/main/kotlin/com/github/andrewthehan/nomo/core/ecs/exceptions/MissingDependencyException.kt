package com.github.andrewthehan.nomo.core.ecs.exceptions

class MissingDependencyException(t: Any, dependencies: Collection<Any>) : Exception("${t} is missing ${dependencies}")