package com.github.andrewthehan.nomo.util

import kotlin.comparisons.maxOf as kotlinMaxOf

@Suppress("UNCHECKED_CAST")
operator fun <NumberType: Number> NumberType.plus(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() + other.toLong()) as NumberType
    is Int -> (this.toInt() + other.toInt()) as NumberType
    is Short -> (this.toShort() + other.toShort()) as NumberType
    is Byte -> (this.toByte() + other.toByte()) as NumberType
    is Double -> (this.toDouble() + other.toDouble()) as NumberType
    is Float -> (this.toFloat() + other.toFloat()) as NumberType
    else -> throw AssertionError("Unknown numeric type")
  }
}

@Suppress("UNCHECKED_CAST")
operator fun <NumberType: Number> NumberType.minus(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() - other.toLong()) as NumberType
    is Int -> (this.toInt() - other.toInt()) as NumberType
    is Short -> (this.toShort() - other.toShort()) as NumberType
    is Byte -> (this.toByte() - other.toByte()) as NumberType
    is Double -> (this.toDouble() - other.toDouble()) as NumberType
    is Float -> (this.toFloat() - other.toFloat()) as NumberType
    else -> throw AssertionError("Unknown numeric type")
  }
}

@Suppress("UNCHECKED_CAST")
operator fun <NumberType: Number> NumberType.times(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() * other.toLong()) as NumberType
    is Int -> (this.toInt() * other.toInt()) as NumberType
    is Short -> (this.toShort() * other.toShort()) as NumberType
    is Byte -> (this.toByte() * other.toByte()) as NumberType
    is Double -> (this.toDouble() * other.toDouble()) as NumberType
    is Float -> (this.toFloat() * other.toFloat()) as NumberType
    else -> throw AssertionError("Unknown numeric type")
  }
}

@Suppress("UNCHECKED_CAST")
operator fun <NumberType: Number> NumberType.div(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() / other.toLong()) as NumberType
    is Int -> (this.toInt() / other.toInt()) as NumberType
    is Short -> (this.toShort() / other.toShort()) as NumberType
    is Byte -> (this.toByte() / other.toByte()) as NumberType
    is Double -> (this.toDouble() / other.toDouble()) as NumberType
    is Float -> (this.toFloat() / other.toFloat()) as NumberType
    else -> throw AssertionError("Unknown numeric type")
  }
}

operator fun <NumberType: Number> NumberType.compareTo(other: NumberType): Int {
  return when (this) {
    is Long -> this.toLong().compareTo(other.toLong())
    is Int -> this.toInt().compareTo(other.toInt())
    is Short -> this.toShort().compareTo(other.toShort())
    is Byte -> this.toByte().compareTo(other.toByte())
    is Double -> this.toDouble().compareTo(other.toDouble())
    is Float -> this.toFloat().compareTo(other.toFloat())
    else -> throw AssertionError("Unknown numeric type")
  }
}

fun <NumberType: Number> NumberType.isPositive() = this.compareTo(0) > 0
fun <NumberType: Number> NumberType.isNegative() = this.compareTo(0) < 0
fun <NumberType: Number> NumberType.isZero() = this.compareTo(0) == 0
