package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.core.ecs.types.Attribute
import com.github.andrewthehan.nomo.util.math.vectors.*
import com.github.andrewthehan.nomo.util.randomId

abstract class Vector1fAttribute(x: Float = 0f) : Vector1f by vectorOf(x), Attribute {
  override val id: String = randomId()
}

abstract class Vector1iAttribute(x: Int = 0) : Vector1i by vectorOf(x), Attribute {
  override val id: String = randomId()
}

abstract class Vector2fAttribute(x: Float = 0f, y: Float = 0f) : Vector2f by vectorOf(x, y), Attribute {
  override val id: String = randomId()
}

abstract class Vector2iAttribute(x: Int = 0, y: Int = 0) : Vector2i by vectorOf(x, y), Attribute {
  override val id: String = randomId()
}

abstract class Vector3fAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3f by vectorOf(x, y, z), Attribute {
  override val id: String = randomId()
}

abstract class Vector3iAttribute(x: Int = 0, y: Int = 0, z: Int = 0) : Vector3i by vectorOf(x, y, z), Attribute {
  override val id: String = randomId()
}

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class MutableVector1fAttribute(x: Float = 0f) : MutableVector1f by mutableVectorOf(x), Attribute {
  override val id: String = randomId()
}

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class MutableVector1iAttribute(x: Int = 0) : MutableVector1i by mutableVectorOf(x), Attribute {
  override val id: String = randomId()
}

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class MutableVector2fAttribute(x: Float = 0f, y: Float = 0f) : MutableVector2f by mutableVectorOf(x, y), Attribute {
  override val id: String = randomId()
}

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class MutableVector2iAttribute(x: Int = 0, y: Int = 0) : MutableVector2i by mutableVectorOf(x, y), Attribute {
  override val id: String = randomId()
}

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class MutableVector3fAttribute(x: Float = 0f, y: Float = 0f, z: Float = 0f) : MutableVector3f by mutableVectorOf(x, y, z), Attribute {
  override val id: String = randomId()
}

// delegate's member and interface's hidden member are the same
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class MutableVector3iAttribute(x: Int = 0, y: Int = 0, z: Int = 0) : MutableVector3i by mutableVectorOf(x, y, z), Attribute {
  override val id: String = randomId()
}