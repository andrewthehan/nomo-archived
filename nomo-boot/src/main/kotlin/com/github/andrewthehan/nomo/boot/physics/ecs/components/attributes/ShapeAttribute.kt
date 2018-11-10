package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.util.math.shapes.*
import com.github.andrewthehan.nomo.util.math.vectors.*

abstract class ShapeAttribute<VectorType : Vector<*, *>>(shape: Shape<VectorType>) : AbstractAttribute(), Shape<VectorType> by shape

open class Shape1fAttribute(shape: Shape<Vector1f>) : ShapeAttribute<Vector1f>(shape)
open class Shape1iAttribute(shape: Shape<Vector1i>) : ShapeAttribute<Vector1i>(shape)
open class Shape2fAttribute(shape: Shape<Vector2f>) : ShapeAttribute<Vector2f>(shape)
open class Shape2iAttribute(shape: Shape<Vector2i>) : ShapeAttribute<Vector2i>(shape)
open class Shape3fAttribute(shape: Shape<Vector3f>) : ShapeAttribute<Vector3f>(shape)
open class Shape3iAttribute(shape: Shape<Vector3i>) : ShapeAttribute<Vector3i>(shape)