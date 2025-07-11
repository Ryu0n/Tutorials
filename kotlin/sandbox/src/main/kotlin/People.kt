package org.example

class People(val name: String) {
    var age: Int? = null
    var city: String? = null

    fun printName() {
        println("My name is ${name}")
    }
}

