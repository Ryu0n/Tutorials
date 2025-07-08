package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.machine.listener.ButtonEventListener
import org.example.menu.Menu
import org.example.menu.ingredient.Ingredient

class Button(
    val menu: Menu,
    override var isActive: Boolean = false,
) : ButtonEventListener {

    fun isMoneyEnough(insertedMoney: Int): Boolean {
        return insertedMoney >= menu.price
    }

    fun isIngredientsEnough(ingredients: List<Ingredient>): Boolean {
        return ingredients.containsAll(menu.ingredients)
    }

    override fun onActivated(e: ActivationEvent) {
        if (isMoneyEnough(e.insertedMoney) && isIngredientsEnough(e.ingredients)) {
            isActive = true
        } else {
            isActive = false
        }
    }
}