package org.example.menu.pizza

import org.example.menu.Menu
import org.example.menu.ingredient.Ingredient

open class Pizza (
    override val name: String,
    var ingredients: MutableList<Ingredient>,
) : Menu(name) {
    override val price: Int = ingredients.sumOf { it.price }
}