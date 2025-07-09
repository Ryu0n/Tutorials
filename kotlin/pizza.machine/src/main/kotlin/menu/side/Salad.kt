package org.example.menu.side

data class Salad(
    override val name: String = "Salad",
    override val price: Int = 3000,
): Side(name, price)
