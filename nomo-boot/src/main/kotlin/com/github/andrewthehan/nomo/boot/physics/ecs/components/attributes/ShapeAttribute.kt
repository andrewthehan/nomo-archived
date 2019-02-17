package com.github.andrewthehan.nomo.boot.physics.ecs.components.attributes

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.AbstractAttribute
import com.github.andrewthehan.nomo.util.math.shapes.Shape
import com.github.andrewthehan.nomo.util.math.vectors.Vector2f

class ShapeAttribute(shape: Shape) : AbstractAttribute(), Shape by shape