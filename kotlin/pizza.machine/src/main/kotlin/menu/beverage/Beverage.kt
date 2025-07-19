package org.example.menu.beverage

import org.example.menu.Menu

open class Beverage(
    override val name: String,
    override val price: Int,
): Menu(name, price)