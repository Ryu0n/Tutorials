package org.example.machine.event

import org.example.menu.ingredient.Ingredient

data class ActivationEvent (
    val insertedMoney: Int,
    val ingredients: MutableList<Ingredient>,
)