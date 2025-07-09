package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.machine.listener.ButtonEventListener
import org.example.menu.Menu
import org.example.menu.ingredient.Ingredient

class Button(
    val menu: Menu,
    override var isActive: Boolean = false,
) : ButtonEventListener {
    var onPurchaseRequested: ((menu: Menu) -> Unit)? = null

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

    override fun onPressed() {
        if (!isActive) {
            println("Button for ${menu.name} is not active. Please check your inserted money or available ingredients.")
            return
        }

        onPurchaseRequested?.invoke(menu)
    }
}