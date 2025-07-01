package org.example.menu

import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Ingredient

class CheesePizza : Menu(
    name = "Cheese Pizza",
    ingredients = mutableListOf<Ingredient>(
        Dough(),
        Cheese(),
        Cheese(),
    )
)