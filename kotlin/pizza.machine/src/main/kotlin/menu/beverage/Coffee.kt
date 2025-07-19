package org.example.menu.beverage

data class Coffee(
    override val name: String = "Coffee",
    override val price: Int = 2000,
) : Beverage(name, price)