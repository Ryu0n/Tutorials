package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.machine.listener.ButtonEventListener
import org.example.menu.pizza.Pizza
import org.example.menu.ingredient.Ingredient

class Button(
    val menu: Pizza,
    override var isActive: Boolean = false,
) : ButtonEventListener {
    var onPurchaseRequested: ((menu: Pizza) -> Unit)? = null

    fun isMoneyEnough(insertedMoney: Int): Boolean {
        return insertedMoney >= menu.price
    }

    fun isIngredientsEnough(ingredients: List<Ingredient>): Boolean {
        val remainedIngredients = ingredients.toMutableList()
        for (ingredient in menu.ingredients) {
            val found = remainedIngredients.indexOfFirst { it.name == ingredient.name }
            if (found > -1) {
                remainedIngredients.removeAt(found)
            } else {
                return false // Not enough ingredients
            }
        }
        return true
    }

    override fun onActivated(e: ActivationEvent) {
        if (isMoneyEnough(e.insertedMoney) && isIngredientsEnough(e.ingredients)) {
            isActive = true
        } else {
            isActive = false
        }
    }

    override fun onPressed() {
        if (!isActive) {
            println("Button for ${menu.name} is not active. Please check your inserted money or available ingredients.")
            return
        }

        onPurchaseRequested?.invoke(menu)
    }
}