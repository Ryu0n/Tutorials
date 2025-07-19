package org.example.menu.pizza

import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Ingredient
import org.example.menu.ingredient.Pepperoni

class PepperoniPizza : Pizza(
    name = "Pepperoni Pizza",
    price = 9000,
    ingredients = mutableListOf<Ingredient>(
        Dough(),
        Cheese(),
        Pepperoni(),
    )
)