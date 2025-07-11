package org.example.menu.set

import org.example.menu.beverage.Cola
import org.example.menu.pizza.CheesePizza

class CheeseColaSet : Set(
    name = "Cheese Cola Set",
    menus = listOf(
        CheesePizza(),
        Cola()
    )
)