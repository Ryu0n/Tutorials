package org.example.menu.set

import org.example.menu.beverage.Cola
import org.example.menu.pizza.CheesePizza
import org.example.menu.side.Potato

class CheeseColaPotatoSet : Set(
    name = "Cheese Cola Set",
    menus = listOf(
        CheesePizza(),
        Cola(),
        Potato()
    )
)