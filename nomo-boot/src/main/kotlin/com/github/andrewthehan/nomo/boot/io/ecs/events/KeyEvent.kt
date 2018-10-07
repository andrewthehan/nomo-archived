package com.github.andrewthehan.nomo.boot.io.ecs.events

import com.github.andrewthehan.nomo.boot.io.Key
import com.github.andrewthehan.nomo.core.ecs.types.EcsId
import com.github.andrewthehan.nomo.core.ecs.types.Event

abstract class KeyEvent(val key: Key, val source: EcsId) : Event

class KeyPressEvent(key: Key, source: EcsId) : KeyEvent(key, source)
class KeyReleaseEvent(key: Key, source: EcsId) : KeyEvent(key, source)
class KeyHoldEvent(key: Key, source: EcsId) : KeyEvent(key, source)