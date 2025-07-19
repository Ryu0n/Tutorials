package org.example.menu.beverage

data class Cola(
    override val name: String = "Cola",
    override val price: Int = 1500,
) : Beverage(name, price)