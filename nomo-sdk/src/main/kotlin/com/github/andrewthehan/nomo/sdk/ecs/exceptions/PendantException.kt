package com.github.andrewthehan.nomo.sdk.ecs.exceptions

class PendantException(target: Any, neighbor: Any) : Exception("Target (${target}) already bound to ${neighbor}")