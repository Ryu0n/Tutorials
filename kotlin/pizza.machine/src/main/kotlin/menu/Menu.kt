package org.example.menu

import org.example.menu.ingredient.Ingredient

open class Menu (
    val name: String,
    val ingredients: List<Ingredient>,
) {
    val price: Int = ingredients.sumOf { it.price }
}