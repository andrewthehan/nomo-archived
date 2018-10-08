package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.util.math.Vector
import com.github.andrewthehan.nomo.util.math.Vector1f
import com.github.andrewthehan.nomo.util.math.Vector1i
import com.github.andrewthehan.nomo.util.math.Vector2f
import com.github.andrewthehan.nomo.util.math.Vector2i
import com.github.andrewthehan.nomo.util.math.Vector3f
import com.github.andrewthehan.nomo.util.math.Vector3i
import com.github.andrewthehan.nomo.util.math.MutableVector
import com.github.andrewthehan.nomo.util.math.MutableVector1f
import com.github.andrewthehan.nomo.util.math.MutableVector1i
import com.github.andrewthehan.nomo.util.math.MutableVector2f
import com.github.andrewthehan.nomo.util.math.MutableVector2i
import com.github.andrewthehan.nomo.util.math.MutableVector3f
import com.github.andrewthehan.nomo.util.math.MutableVector3i

open class ShapeAttribute(open val points: List<Vector<*>>) : AbstractAttribute()

open class Shape1fAttribute(override val points: List<Vector1f>) : ShapeAttribute(points)

open class Shape1iAttribute(override val points: List<Vector1i>) : ShapeAttribute(points)

open class Shape2fAttribute(override val points: List<Vector2f>) : ShapeAttribute(points)

open class Shape2iAttribute(override val points: List<Vector2i>) : ShapeAttribute(points)

open class Shape3fAttribute(override val points: List<Vector3f>) : ShapeAttribute(points)

open class Shape3iAttribute(override val points: List<Vector3i>) : ShapeAttribute(points)

open class MutableShapeAttribute(override val points: MutableList<out MutableVector<*>>) : ShapeAttribute(points)

open class MutableShape1fAttribute(override val points: MutableList<MutableVector1f>) : MutableShapeAttribute(points)

open class MutableShape1iAttribute(override val points: MutableList<MutableVector1i>) : MutableShapeAttribute(points)

open class MutableShape2fAttribute(override val points: MutableList<MutableVector2f>) : MutableShapeAttribute(points)

open class MutableShape2iAttribute(override val points: MutableList<MutableVector2i>) : MutableShapeAttribute(points)

open class MutableShape3fAttribute(override val points: MutableList<MutableVector3f>) : MutableShapeAttribute(points)

open class MutableShape3iAttribute(override val points: MutableList<MutableVector3i>) : MutableShapeAttribute(points)