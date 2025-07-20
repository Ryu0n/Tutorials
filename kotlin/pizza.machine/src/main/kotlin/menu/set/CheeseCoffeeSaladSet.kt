package org.example.menu.set

import org.example.menu.beverage.Coffee
import org.example.menu.pizza.CheesePizza
import org.example.menu.side.Salad

class CheeseCoffeeSaladSet : Set(
    name = "Cheese Coffee Salad Set",
    price = 11500,
    menus = listOf(
        CheesePizza(),
        Coffee(),
        Salad()
    )
)