package org.example.machine.event

import org.example.menu.ingredient.Ingredient
import org.example.menu.side.Side

data class ActivationEvent (
    val insertedMoney: Int,
    val ingredients: MutableList<Ingredient>,
    val sides: MutableList<Side>,
)