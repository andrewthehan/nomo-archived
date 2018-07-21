package com.github.andrewthehan.nomo.core.ecs.types

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable
import com.github.andrewthehan.nomo.core.ecs.managers.EcsManager

abstract class System(id: EcsId, val ecsManager: EcsManager) : EcsObject(id), Updatable<Float>