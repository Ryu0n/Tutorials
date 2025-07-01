package org.example.menu

import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Ingredient
import org.example.menu.ingredient.Pepperoni

class PepperoniPizza : Menu(
    name = "Pepperoni Pizza",
    ingredients = mutableListOf<Ingredient>(
        Dough(),
        Cheese(),
        Pepperoni(),
    )
)