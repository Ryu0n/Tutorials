package org.example

open class Animal(val name: String) {
    open var sound = "Woof"

    fun printInfo() {
        println("My name is $name")
    }

    open fun bark(sound: String?) {
        if (sound != null) {
            this.sound = sound
        }
        println("Animal bark ... ${this.sound}")
    }
}

class Dog(name: String) : Animal(name) {

    override var sound = "Bow Wow"

    override fun bark(sound: String?) {
        if (sound != null) {
            this.sound = sound
        }
        println("Dog barks ... ${this.sound}")
    }
}