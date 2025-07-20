package org.example.menu.side

data class HotWing(
    override val name: String = "Hot Wing",
    override val price: Int = 2500,
) : Side(name, price)
