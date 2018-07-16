package com.github.andrewthehan.nomo.util

@Suppress("Unchecked_cast")
operator fun <NumberType: Number> NumberType.plus(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() + other.toLong()) as NumberType
    is Int -> (this.toInt()  + other.toInt()) as NumberType
    is Short -> (this.toShort() + other.toShort()) as NumberType
    is Byte -> (this.toByte() + other.toByte()) as NumberType
    is Double -> (this.toDouble() + other.toDouble()) as NumberType
    is Float -> (this.toFloat() + other.toFloat()) as NumberType
    else -> throw RuntimeException("Unknown numeric type")
  }
}

@Suppress("Unchecked_cast")
operator fun <NumberType: Number> NumberType.minus(other: NumberType): NumberType {
  return when (this) {
    is Long -> (this.toLong() - other.toLong()) as NumberType
    is Int -> (this.toInt()  - other.toInt()) as NumberType
    is Short -> (this.toShort() - other.toShort()) as NumberType
    is Byte -> (this.toByte() - other.toByte()) as NumberType
    is Double -> (this.toDouble() - other.toDouble()) as NumberType
    is Float -> (this.toFloat() - other.toFloat()) as NumberType
    else -> throw RuntimeException("Unknown numeric type")
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
    else -> throw RuntimeException("Unknown numeric type")
  }
}