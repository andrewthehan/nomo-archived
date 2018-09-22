package com.github.andrewthehan.nomo.sdk.ecs.exceptions

class MissingDependencyException(t: Any, dependencies: Collection<Any>) : Exception("${t} is missing ${dependencies}")