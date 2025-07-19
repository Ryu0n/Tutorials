package org.example.promotion

import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Ingredient
import org.example.menu.pizza.Pizza
import java.time.LocalDateTime

class DoubleCheesePromotion(
    override val name: String = "Double Cheese Promotion",
    override val startDate: LocalDateTime = LocalDateTime.of(2023, 10, 1, 0, 0),
    override val period: Int = 30,
    override val probability: Int = 50,
): Promotion {
    override fun isApplicable(pizza: Pizza, remainedIngredients: List<Ingredient>): Boolean {
        if (!isActive()) {
            println("Promotion [$name] is not active.")
            return false
        }
        val cheeseCount = pizza.ingredients.count { it is Cheese }
        val remainedCheeseCount = remainedIngredients.count { it is Cheese }
        if ((cheeseCount * 2) > remainedCheeseCount) {
            println("Not enough cheese in the remained ingredients for ${name}.")
            return false
        }
        for (ingredient in pizza.ingredients) {
            if (ingredient is Cheese) {
                return true
            }
        }
        println("Pizza does not contain cheese, [${name}] cannot be applied.")
        return false
    }

    override fun apply(pizza: Pizza, remainedIngredients: MutableList<Ingredient>): List<Ingredient> {
        val pizzaIngredients = mutableListOf<Ingredient>()
        for (ingredient in pizza.ingredients) {
            pizzaIngredients.add(ingredient)
        }
        if (isApplicable(pizza, remainedIngredients) && (0..99).random() < probability) {
            val cheeseCount = pizza.ingredients.count { it is Cheese }
            println("[🥳] Congratulations! [${name}] applied!. (Added $cheeseCount 🧀 by promotion)")
            return pizzaIngredients + List(cheeseCount) { Cheese() }
        } else {
            println("Promotion [${name}] cannot be applied.")
            return pizzaIngredients
        }
    }
}