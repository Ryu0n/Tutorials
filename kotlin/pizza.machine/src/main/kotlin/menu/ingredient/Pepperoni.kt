package org.example.menu.ingredient

data class Pepperoni(
    override val name: String = "Pepperoni",
    override val price: Int = 3000,
    override val isEnableAdditionalPurchase: Boolean = true,
) : Ingredient