package org.example.menu.set

import org.example.menu.Menu

open class Set(
    override val name: String,
    val menus: List<Menu>,
): Menu(name) {
    override val price: Int = menus.sumOf { it.price }
}