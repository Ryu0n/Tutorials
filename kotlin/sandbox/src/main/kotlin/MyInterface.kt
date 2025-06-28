package org.example


interface MyInterface {
    val prop: Int // abstract property

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        println(prop)
    }

    fun bar(): Unit
}


class Child : MyInterface {
    override val prop: Int = 29

    override fun bar(): Unit = println("Child")
}