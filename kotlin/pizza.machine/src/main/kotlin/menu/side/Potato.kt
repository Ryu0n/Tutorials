package org.example.menu.side

data class Potato(
    override val name: String = "Potato",
    override val price: Int = 2000,
) : Side(name, price)