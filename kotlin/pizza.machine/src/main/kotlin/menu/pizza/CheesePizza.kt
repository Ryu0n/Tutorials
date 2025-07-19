package org.example.menu.pizza

import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Ingredient

class CheesePizza : Pizza(
    name = "Cheese Pizza",
    price = 8000,
    ingredients = mutableListOf<Ingredient>(
        Dough(),
        Cheese(),
        Cheese(),
    )
)