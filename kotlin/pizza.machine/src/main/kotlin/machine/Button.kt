package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.machine.listener.ButtonEventListener
import org.example.menu.Menu
import org.example.menu.beverage.Beverage
import org.example.menu.pizza.Pizza
import org.example.menu.ingredient.Ingredient
import org.example.menu.side.Side
import org.example.menu.set.Set

class Button(
    val menu: Menu,
    override var isActive: Boolean = false,
) : ButtonEventListener {
    var onPurchaseRequested: ((menu: Menu) -> Unit)? = null

    fun isMoneyEnough(insertedMoney: Int): Boolean {
        return insertedMoney >= menu.price
    }

    fun isPizzaIngredientsEnough(ingredients: List<Ingredient>): Boolean {
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

    fun isSetIngredientsEnough(ingredients: List<Ingredient>): Boolean {
        if (menu !is Set) {
            return false // Only Set type is supported
        }
        var requiredIngredients = emptyList<Ingredient>()
        val remainedIngredients = ingredients.toMutableList()
        for (subMenu in menu.menus) {
            if (subMenu !is Pizza) continue
            requiredIngredients = requiredIngredients + subMenu.ingredients
        }
        for (ingredient in requiredIngredients) {
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
        isActive = false
        if (menu is Pizza && isMoneyEnough(e.insertedMoney) && isPizzaIngredientsEnough(e.ingredients)) {
            isActive = true
        } else if (menu is Beverage || menu is Side) {
            if (isMoneyEnough(e.insertedMoney)) {
                isActive = true
            }
        } else if (menu is Set && isMoneyEnough(e.insertedMoney) && isSetIngredientsEnough(e.ingredients)) {
            isActive = true
        }
    }

    override fun onPressed() {
        if (!isActive) {
            println("Button for [${menu.name}] is not active. Please check your inserted money or available ingredients.")
            return
        }
        onPurchaseRequested?.invoke(menu)
    }
}