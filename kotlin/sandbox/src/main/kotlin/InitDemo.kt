package org.example


class InitDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)
    init {
        println("First initializer block $name")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)
    init {
        println("Second initializer block ${name.length}")
    }
}