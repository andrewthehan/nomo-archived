package com.github.andrewthehan.nomo.util.math.vectors

fun zeroMutableVector1f() = MutableVector1fInternal(0f)
fun zeroMutableVector2f() = MutableVector2fInternal(0f, 0f)
fun zeroMutableVector3f() = MutableVector3fInternal(0f, 0f, 0f)
fun zeroMutableVector1i() = MutableVector1iInternal(0)
fun zeroMutableVector2i() = MutableVector2iInternal(0, 0)
fun zeroMutableVector3i() = MutableVector3iInternal(0, 0, 0)

fun mutableVectorOf(x: Float) = MutableVector1fInternal(x)
fun mutableVectorOf(x: Float, y: Float) = MutableVector2fInternal(x, y)
fun mutableVectorOf(x: Float, y: Float, z: Float) = MutableVector3fInternal(x, y, z)
fun mutableVectorOf(x: Int) = MutableVector1iInternal(x)
fun mutableVectorOf(x: Int, y: Int) = MutableVector2iInternal(x, y)
fun mutableVectorOf(x: Int, y: Int, z: Int) = MutableVector3iInternal(x, y, z)

interface MutableVector1f : Vector1f, MutableVector1<Float>
interface MutableVector2f : Vector2f, MutableVector2<Float>
interface MutableVector3f : Vector3f, MutableVector3<Float>

interface MutableVector1i : Vector1i, MutableVector1<Int>
interface MutableVector2i : Vector2i, MutableVector2<Int>
interface MutableVector3i : Vector3i, MutableVector3<Int>

interface MutableVector1<NumberType : Number> : Vector1<NumberType>, MutableVector<NumberType> {
  override open var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }
}

interface MutableVector2<NumberType : Number> : Vector2<NumberType>, MutableVector<NumberType> {
  override var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }
  override var y: NumberType
    get() = this[1]
    set(value: NumberType) {
      this[1] = value
    }
}

interface MutableVector3<NumberType : Number> : Vector3<NumberType>, MutableVector<NumberType> {
  override var x: NumberType
    get() = this[0]
    set(value: NumberType) {
      this[0] = value
    }
  override var y: NumberType
    get() = this[1]
    set(value: NumberType) {
      this[1] = value
    }
  override var z: NumberType
    get() = this[2]
    set(value: NumberType) {
      this[2] = value
    }
}

class MutableVector1fInternal(x: Float) : AbstractMutableVector<Float>(x), MutableVector1f {
  override val vectorProducer: (elementProvider: (Int) -> Float) -> MutableVector1f = { MutableVector1fInternal(it(0)) }
}

class MutableVector2fInternal(x: Float, y: Float) : AbstractMutableVector<Float>(x, y), MutableVector2f {
  override val vectorProducer: (elementProvider: (Int) -> Float) -> MutableVector2f = { MutableVector2fInternal(it(0), it(1)) }
}

class MutableVector3fInternal(x: Float, y: Float, z: Float) : AbstractMutableVector<Float>(x, y, z), MutableVector3f {
  override val vectorProducer: (elementProvider: (Int) -> Float) -> MutableVector3f = { MutableVector3fInternal(it(0), it(1), it(2)) }
}

class MutableVector1iInternal(x: Int) : AbstractMutableVector<Int>(x), MutableVector1i {
  override val vectorProducer: (elementProvider: (Int) -> Int) -> MutableVector1i = { MutableVector1iInternal(it(0)) }
}

class MutableVector2iInternal(x: Int, y: Int) : AbstractMutableVector<Int>(x, y), MutableVector2i {
  override val vectorProducer: (elementProvider: (Int) -> Int) -> MutableVector2i = { MutableVector2iInternal(it(0), it(1)) }
}

class MutableVector3iInternal(x: Int, y: Int, z: Int) : AbstractMutableVector<Int>(x, y, z), MutableVector3i {
  override val vectorProducer: (elementProvider: (Int) -> Int) -> MutableVector3i = { MutableVector3iInternal(it(0), it(1), it(2)) }
}