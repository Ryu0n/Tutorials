package org.example.menu.set

import org.example.menu.beverage.Coffee
import org.example.menu.pizza.CheesePizza

class CheeseCoffeeSet : Set(
    name = "Cheese Coffee Set",
    menus = listOf(
        CheesePizza(),
        Coffee()
    )
)