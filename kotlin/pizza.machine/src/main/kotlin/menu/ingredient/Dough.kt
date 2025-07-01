package org.example.menu.ingredient

data class Dough(
    override val name: String = "Dough",
    override val price: Int = 1000,
) : Ingredient
