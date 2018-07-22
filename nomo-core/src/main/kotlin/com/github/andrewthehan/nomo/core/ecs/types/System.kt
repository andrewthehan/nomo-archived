package com.github.andrewthehan.nomo.core.ecs.types

import com.github.andrewthehan.nomo.core.ecs.interfaces.Updatable

interface System : EcsObject, Updatable<Float>