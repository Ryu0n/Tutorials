package org.example.menu.beverage

data class Beer(
    override val name: String = "Beer",
    override val price: Int = 3000,
) : Beverage(name, price)
