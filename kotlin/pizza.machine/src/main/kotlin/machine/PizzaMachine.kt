package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.menu.beverage.Beverage
import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Ingredient
import org.example.menu.pizza.Pizza
import org.example.menu.side.Side
import org.example.menu.set.Set
import org.example.promotion.DoubleCheesePromotion
import org.example.promotion.Promotion

class PizzaMachine(
    val delimeterRepeat: Int = 100,
    var insertedMoney: Int = 0,
    val buttons: MutableList<Button>,
    val ingredients: MutableList<Ingredient>,
    val promotions: MutableList<Promotion>,
) {
    val menuEmojiMap = mutableMapOf<String, String>(
        "Pizza" to "🍕",
        "Beverage" to "☕️",
        "Side" to "🍟",
        "Set" to "🍽",
    )
    val ingredientEmojiMap = mutableMapOf<String, String>(
        "Cheese" to "🧀",
        "Pepperoni" to "🍕",
        "Dough" to "🍞",
    )

    fun applyPromotion(promotion: Promotion, menu: Pizza): MutableList<Ingredient> {
        // Shallow copy
        val appliedIngredients = mutableListOf<Ingredient>()
        menu.ingredients.forEach { ingredient ->
            appliedIngredients.add(ingredient)
        }
        if (promotion is DoubleCheesePromotion && promotion.isApplicable(menu, ingredients)) {
            // Apply the promotion with 40% probability
            if ((0..9).random() < 7) {
                val cheeseCount = menu.ingredients.count { it.name == "Cheese" }
                println("[🥳] Congratulations! [${promotion.name}] applied!. (Added $cheeseCount 🧀 by promotion)")
                appliedIngredients.addAll( List(cheeseCount) { Cheese() })
            }
        }
        return appliedIngredients
    }

    init {
        buttons.forEach { button ->
            button.onPurchaseRequested = { menu ->
                insertedMoney -= menu.price
                if (menu is Pizza) {
                    val requiredIngredients = mutableListOf<Ingredient>()
                    for (promotion in promotions) {
                        requiredIngredients.addAll(
                            applyPromotion(promotion, menu)
                        )
                    }
                    requiredIngredients.forEach { ingredients.remove(it) }
                }
                if (menu is Set) {
                    menu.menus.forEach { subMenu ->
                        if (subMenu is Pizza) {
                            subMenu.ingredients.forEach { ingredients.remove(it) }
                        }
                    }
                }
                println("[💰] You have purchased [${button.menu.name}].")
                showRemainedAssets()
                if (menu is Pizza) {
                    purchaseAdditionalIngredient()
                }
                refreshButtonStates()
            }
        }
    }

    // TODO: Ref Command Pattern
    fun refreshButtonStates() {
        for (listener in buttons) {
            listener.onActivated(
                ActivationEvent(
                    insertedMoney = insertedMoney,
                    ingredients = ingredients,
                )
            )
        }
    }

    fun purchaseAdditionalIngredient() {
        while (true) {
            println("[🍳] Do you want to purchase an additional ingredient? (yes/no)")
            val input = readLine()?.lowercase()
            if (input == "no") {
                println("[😂] No additional ingredients purchased.")
                break
            } else if (input == "yes") {
                showRemainedIngredients()
                println("[🍳] Please enter the name of the ingredient:")
                val ingredientName = readLine()
                // Copy of ingredients to avoid concurrent modification exception
                val remainedIngredient = ingredients.toMutableList()
                for (ingredient in remainedIngredient) {
                    if (ingredient.name == ingredientName) {
                        if (insertedMoney < ingredient.price) {
                            println("[❌] Not enough money to purchase $ingredientName. Please insert more money.")
                            return
                        }
                        insertedMoney -= ingredient.price
                        ingredients.remove(ingredient)
                        println("[🍳] Purchase complete! Added $ingredient.")
                        break
                    }
                }
                if (remainedIngredient.none { it.name == ingredientName }) {
                    println("[❌] Ingredient $ingredientName not found. Please try another ingredient.")
                }
            } else {
                println("[❌] Invalid input. Please type 'yes' or 'no'.")
            }
        }
    }

    fun onMoneyInserted() {
        println("\n" + "=".repeat(delimeterRepeat))
        println("[💰] Please insert money (₩):")
        val money = readLine()?.toIntOrNull()

        money?.let {
            if (it < 0) {
                return println("[❌] Please insert a valid amount of money.")
            }
        }
        insertedMoney += money ?: 0
        refreshButtonStates()
    }

    fun showRemainedIngredients() {
        println("[🍳] Remained ingredients:")
        val ingredientCountMap = mutableMapOf<String, Int>()
        for (ingredient in ingredients) {
            ingredientCountMap[ingredient.name] = ingredientCountMap.getOrDefault(ingredient.name, 0) + 1
        }
        for (ingredientCount in ingredientCountMap) {
            println("- ${ingredientEmojiMap[ingredientCount.key]} ${ingredientCount.key} (Count: ${ingredientCount.value}), Price: ₩${ingredients.firstOrNull { it.name == ingredientCount.key }?.price ?: 0})")
        }
    }
    
    fun showRemainedAssets() {
        println("[💵] Current inserted money: ₩$insertedMoney")
        showRemainedIngredients()
    }

    fun showAvailableMenu() {
        println("\n" + "=".repeat(delimeterRepeat))
        showRemainedAssets()
        println("[🔴] Current available buttons as below:")
        for (index in buttons.indices) {
            if (!buttons[index].isActive) continue
            val menu = buttons[index].menu
            if (menu is Pizza) {
                println("$index. ${menuEmojiMap["Pizza"]} ${menu.name} - Price: ₩${menu.price} (Ingredients: ${menu.ingredients.joinToString(", ") { it.name }})")
            } else if (menu is Beverage) {
                println("$index. ${menuEmojiMap["Beverage"]}️ ${menu.name} - Price: ₩${menu.price}")
            } else if (menu is Side) {
                println("$index. ${menuEmojiMap["Side"]} ${menu.name} - Price: ₩${menu.price}")
            } else if (menu is Set) {
                println("$index. ${menuEmojiMap["Set"]}️ ${menu.name} - Price: ₩${menu.price} (Menus: ${menu.menus.joinToString(", ") { it.name }})")
            }
        }
    }

    fun divMod(a: Int, b: Int): Pair<Int, Int> {
        return Pair(a / b, a % b)
    }

    fun returnChange() {
        val (div10000, mod10000) = divMod(insertedMoney, 10000)
        val (div5000, mod5000) = divMod(mod10000, 5000)
        val (div1000, mod1000) = divMod(mod5000, 1000)
        val (div500, mod500) = divMod(mod1000, 500)
        val (div100, mod100) = divMod(mod500, 100)

        println("[💰] You have received ₩$insertedMoney :")
        println("$div10000 x ₩10000 | $div5000 x ₩5000 | $div1000 x ₩1000 | $div500 x ₩500 | $div100 x ₩100 | and ₩$mod100 as change.")
    }

    fun onButtonSelected(): Int {
        println("\n" + "=".repeat(delimeterRepeat))
        print("[🔴] Please select a button by index (or type 'exit' to quit): ")
        val input = readLine()
        if (input == null || input.lowercase() == "exit") {
            returnChange()
            println("Exiting the machine. Thank you!")
            return -1 // Indicating exit
        }
        return input.toInt()
    }
}