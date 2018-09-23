package com.github.andrewthehan.nomo.boot.ecs.events

import com.github.andrewthehan.nomo.core.ecs.types.Event

data class DamageEvent<NumberType: Number>(var amount: NumberType) : Event