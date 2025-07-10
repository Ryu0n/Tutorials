package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.machine.listener.ButtonEventListener
import org.example.menu.Menu
import org.example.menu.beverage.Beverage
import org.example.menu.pizza.Pizza
import org.example.menu.ingredient.Ingredient
import org.example.menu.side.Side

class Button(
    val menu: Menu,
    override var isActive: Boolean = false,
) : ButtonEventListener {
    var onPurchaseRequested: ((menu: Menu) -> Unit)? = null

    fun isMoneyEnough(insertedMoney: Int): Boolean {
        return insertedMoney >= menu.price
    }

    fun isIngredientsEnough(ingredients: List<Ingredient>): Boolean {
        val remainedIngredients = ingredients.toMutableList()
        // Smart cast menu to Pizza
        if (menu !is Pizza) {
            return false // Only Pizza type is supported
        }
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
        if (menu is Pizza) {
            if (isMoneyEnough(e.insertedMoney) && isIngredientsEnough(e.ingredients)) {
                isActive = true
            } else {
                isActive = false
            }
        } else if (menu is Beverage || menu is Side) {
            if (isMoneyEnough(e.insertedMoney)) {
                isActive = true
            } else {
                isActive = false
            }
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