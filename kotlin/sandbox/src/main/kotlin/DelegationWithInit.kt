package org.example


class DelegationWithInit(val name: String) {
    var age: Int = 0
    var desc: String = "No description"

    init {
        println("My name is $name")
    }

    constructor(name: String, age: Int) : this(name) {
        this.age = age
        println("My age is $age")
    }

    constructor(name: String, age: Int, desc: String) : this(name, age) {
        this.desc = desc
        println("My description is $desc")
    }

    init {
        println("Initialization block 1")
    }
}