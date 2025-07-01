package org.example.menu

import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Pepperoni

class CheesePepperoniPizza : Menu(
    name = "Cheese Pepperoni Pizza",
    ingredients = mutableListOf(
        Dough(),
        Cheese(),
        Cheese(),
        Pepperoni()
    )
)