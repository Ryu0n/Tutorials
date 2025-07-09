package org.example.menu

abstract class Menu(
    open val name: String,
) {
    abstract val price: Int
}