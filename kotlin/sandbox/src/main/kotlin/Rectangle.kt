package org.example

// This is a simple example of a class in Kotlin
// open class is a class that can be inherited from
open class Shape

// Rectangle is a class that inherits from Shape
// Two properties are automatically generated: height and length
class Rectangle(val height: Double, val length: Double): Shape() {
    val perimeter = (height + length) * 2
}

class Square(val side: Double): Shape() {
    val perimeter = side * 4

    fun area(): Double {
        return side * side
    }
}