package org.example.menu.side

data class CheeseBall(
    override val name: String = "Cheese Ball",
    override val price: Int = 3000,
) : Side(name, price)
