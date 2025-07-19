package org.example.menu.pizza

import org.example.menu.Menu
import org.example.menu.ingredient.Ingredient

open class Pizza (
    override val name: String,
    override val price: Int,
    var ingredients: MutableList<Ingredient>,
) : Menu(name, price)