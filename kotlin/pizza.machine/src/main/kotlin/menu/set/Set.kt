package org.example.menu.set

import org.example.menu.Menu

open class Set(
    override val name: String,
    override val price: Int,
    val menus: List<Menu>,
): Menu(name, price)