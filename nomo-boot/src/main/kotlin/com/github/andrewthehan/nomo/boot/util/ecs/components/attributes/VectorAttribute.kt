package com.github.andrewthehan.nomo.boot.util.ecs.components.attributes

import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.util.math.Vector1f
import com.github.andrewthehan.nomo.util.math.Vector1i
import com.github.andrewthehan.nomo.util.math.Vector2f
import com.github.andrewthehan.nomo.util.math.Vector2i
import com.github.andrewthehan.nomo.util.math.Vector3f
import com.github.andrewthehan.nomo.util.math.Vector3i
import com.github.andrewthehan.nomo.util.math.MutableVector1f
import com.github.andrewthehan.nomo.util.math.MutableVector1i
import com.github.andrewthehan.nomo.util.math.MutableVector2f
import com.github.andrewthehan.nomo.util.math.MutableVector2i
import com.github.andrewthehan.nomo.util.math.MutableVector3f
import com.github.andrewthehan.nomo.util.math.MutableVector3i
import com.github.andrewthehan.nomo.util.randomId

open class Vector1fAttribute(x: Float = 0f) : Vector1f(x), Attribute {
  override val id: String = randomId()
}

open class Vector1iAttribute(x: Int = 0) : Vector1i(x), Attribute {
  override val id: String = randomId()
}

open class Vector2fAttribute(x: Float = 0f, y: Float = 0f) : Vector2f(x, y), Attribute {
  override val id: String = randomId()
}

open class Vector2iAttribute(x: Int = 0, y: Int = 0) : Vector2i(x, y), Attribute {
  override val id: String = randomId()
}

open class Vector3fAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3f(x, y, z), Attribute {
  override val id: String = randomId()
}

open class Vector3iAttribute(x: Int = 0, y: Int = 0, z: Int = 0) : Vector3i(x, y, z), Attribute {
  override val id: String = randomId()
}

open class MutableVector1fAttribute(x: Float = 0f) : MutableVector1f(x), Attribute {
  override val id: String = randomId()
}

open class MutableVector1iAttribute(x: Int = 0) : MutableVector1i(x), Attribute {
  override val id: String = randomId()
}

open class MutableVector2fAttribute(x: Float = 0f, y: Float = 0f) : MutableVector2f(x, y), Attribute {
  override val id: String = randomId()
}

open class MutableVector2iAttribute(x: Int = 0, y: Int = 0) : MutableVector2i(x, y), Attribute {
  override val id: String = randomId()
}

open class MutableVector3fAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3f(x, y, z), Attribute {
  override val id: String = randomId()
}

open class MutableVector3iAttribute(x: Int = 0, y: Int = 0, z: Int = 0) : MutableVector3i(x, y, z), Attribute {
  override val id: String = randomId()
}