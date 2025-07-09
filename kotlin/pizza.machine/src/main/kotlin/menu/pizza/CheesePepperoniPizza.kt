package org.example.menu.pizza

import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Pepperoni

class CheesePepperoniPizza : Pizza(
    name = "Cheese Pepperoni Pizza",
    ingredients = mutableListOf(
        Dough(),
        Cheese(),
        Cheese(),
        Pepperoni()
    )
)