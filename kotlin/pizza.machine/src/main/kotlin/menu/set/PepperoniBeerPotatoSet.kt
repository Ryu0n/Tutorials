package org.example.menu.set

class PepperoniBeerPotatoSet : Set(
    name = "Pepperoni Beer Potato Set",
    price = 13000,
    menus = listOf(
        org.example.menu.pizza.PepperoniPizza(),
        org.example.menu.beverage.Beer(),
        org.example.menu.side.Potato()
    )
)