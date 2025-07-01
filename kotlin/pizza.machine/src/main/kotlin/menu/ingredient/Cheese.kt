package org.example.menu.ingredient

data class Cheese(
    override val name: String = "Cheese",
    override val price: Int = 2000,
) : Ingredient
