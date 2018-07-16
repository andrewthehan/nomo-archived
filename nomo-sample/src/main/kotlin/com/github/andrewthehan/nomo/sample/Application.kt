package com.github.andrewthehan.nomo.sample

import com.github.andrewthehan.nomo.sdk.ecs.components.attributes.HealthAttribute

fun main(args: Array<String>) {
  println("Hello, world!")

  val healthAttribute = HealthAttribute(100)
  println("Id is ${healthAttribute.id.value}")
  println("I have ${healthAttribute.value} health.")

  healthAttribute.damage(50)
  println("Oof, I got hit ${50} damage. Now I have ${healthAttribute.value} health.")

  healthAttribute.heal(25)
  println("I got healed ${25} health. Now I have ${healthAttribute.value} health.")

  println("It is ${healthAttribute.isAlive()} that I am alive.")
  println("It is ${healthAttribute.isDead()} that I am dead.")

  val floatHealthAttribute = HealthAttribute(100.0f)
  println("Id is ${floatHealthAttribute.id.value}")
  println("I have ${floatHealthAttribute.value} health.")

  floatHealthAttribute.damage(25.3f)
  println("Oof, I got hit ${25.3f} damage. Now I have ${floatHealthAttribute.value} health.")

  println("It is ${floatHealthAttribute.isAlive()} that I am alive.")
  println("It is ${floatHealthAttribute.isDead()} that I am dead.")

  floatHealthAttribute.damage(100f)
  println("Oof, I got hit ${100f} damage. Now I have ${floatHealthAttribute.value} health.")
  println("It is ${floatHealthAttribute.isAlive()} that I am alive.")
  println("It is ${floatHealthAttribute.isDead()} that I am dead.")
}
