package com.github.andrewthehan.nomo.util.math

@Suppress("UNCHECKED_CAST")
fun <NumberType : Number> Number.cast(): NumberType {
  return when (this) {
    is Long -> this.toLong() as NumberType
    is Int -> this.toInt() as NumberType
    is Short -> this.toShort() as NumberType
    is Byte -> this.toByte() as NumberType
    is Double -> this.toDouble() as NumberType
    is Float -> this.toFloat() as NumberType
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}

fun <NumberType : Number> zero() = 0.cast<NumberType>()

fun <NumberType : Number> Array<NumberType>.sum(): NumberType 
  = this.fold(zero()) { sum, x -> sum + x }

fun <NumberType : Number> Iterable<NumberType>.sum(): NumberType
  = this.fold(zero()) { sum, x -> sum + x }

fun <NumberType : Number> NumberType.isPositive() = this.compareTo(0) > 0
fun <NumberType : Number> NumberType.isNegative() = this.compareTo(0) < 0
fun <NumberType : Number> NumberType.isZero() = this.compareTo(0) == 0

operator fun <NumberType : Number> NumberType.plus(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() + other.toLong()).cast<NumberType>()
    is Int -> (this.toInt() + other.toInt()).cast<NumberType>()
    is Short -> (this.toShort() + other.toShort()).cast<NumberType>()
    is Byte -> (this.toByte() + other.toByte()).cast<NumberType>()
    is Double -> (this.toDouble() + other.toDouble()).cast<NumberType>()
    is Float -> (this.toFloat() + other.toFloat()).cast<NumberType>()
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}

operator fun <NumberType : Number> NumberType.minus(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() - other.toLong()).cast<NumberType>()
    is Int -> (this.toInt() - other.toInt()).cast<NumberType>()
    is Short -> (this.toShort() - other.toShort()).cast<NumberType>()
    is Byte -> (this.toByte() - other.toByte()).cast<NumberType>()
    is Double -> (this.toDouble() - other.toDouble()).cast<NumberType>()
    is Float -> (this.toFloat() - other.toFloat()).cast<NumberType>()
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}

operator fun <NumberType : Number> NumberType.times(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() * other.toLong()).cast<NumberType>()
    is Int -> (this.toInt() * other.toInt()).cast<NumberType>()
    is Short -> (this.toShort() * other.toShort()).cast<NumberType>()
    is Byte -> (this.toByte() * other.toByte()).cast<NumberType>()
    is Double -> (this.toDouble() * other.toDouble()).cast<NumberType>()
    is Float -> (this.toFloat() * other.toFloat()).cast<NumberType>()
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}

operator fun <NumberType : Number> NumberType.div(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() / other.toLong()).cast<NumberType>()
    is Int -> (this.toInt() / other.toInt()).cast<NumberType>()
    is Short -> (this.toShort() / other.toShort()).cast<NumberType>()
    is Byte -> (this.toByte() / other.toByte()).cast<NumberType>()
    is Double -> (this.toDouble() / other.toDouble()).cast<NumberType>()
    is Float -> (this.toFloat() / other.toFloat()).cast<NumberType>()
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}

operator fun <NumberType : Number> NumberType.unaryPlus(): NumberType {
  return this
}

operator fun <NumberType : Number> NumberType.unaryMinus(): NumberType {
  return when (this) {
    is Long -> (-this.toLong()).cast<NumberType>()
    is Int -> (-this.toInt()).cast<NumberType>()
    is Short -> (-this.toShort()).cast<NumberType>()
    is Byte -> (-this.toByte()).cast<NumberType>()
    is Double -> (-this.toDouble()).cast<NumberType>()
    is Float -> (-this.toFloat()).cast<NumberType>()
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}

operator fun <NumberType : Number> NumberType.compareTo(other: NumberType): Int {
  return when (this) {
    is Long -> this.toLong().compareTo(other.toLong())
    is Int -> this.toInt().compareTo(other.toInt())
    is Short -> this.toShort().compareTo(other.toShort())
    is Byte -> this.toByte().compareTo(other.toByte())
    is Double -> this.toDouble().compareTo(other.toDouble())
    is Float -> this.toFloat().compareTo(other.toFloat())
    else -> throw AssertionError("Unknown numeric type: ${this::class}")
  }
}
