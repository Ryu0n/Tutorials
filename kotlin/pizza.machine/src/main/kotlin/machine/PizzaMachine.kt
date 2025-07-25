package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.menu.beverage.Beverage
import org.example.menu.ingredient.Ingredient
import org.example.menu.pizza.Pizza
import org.example.menu.side.Side
import org.example.menu.set.Set
import org.example.promotion.Promotion

class PizzaMachine(
    val delimeterRepeat: Int = 100,
    var insertedMoney: Int = 0,
    val buttons: MutableList<Button>,
    val ingredients: MutableList<Ingredient>,
    val sides: MutableList<Side>,
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
    val sideEmojiMap = mutableMapOf<String, String>(
        "Potato" to "🥔",
        "Salad" to "🥗",
        "Hot Wing" to "🍗",
        "Cheese Ball" to "🧀",
    )
    val beverageEmojiMap = mutableMapOf<String, String>(
        "Cola" to "🥤",
        "Coffee" to "☕️",
        "Beer" to "🍺",
    )

    init {
        buttons.forEach { button ->
            button.onPurchaseRequested = { menu ->
                insertedMoney -= menu.price
                if (menu is Pizza) {
                    val requiredIngredients = mutableListOf<Ingredient>()
                    for (promotion in promotions) {
                        requiredIngredients.addAll(
                            promotion.apply(menu, ingredients)
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
                if (menu is Side) {
                    sides.remove(menu)
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
                    sides = sides,
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
                showRemainedIngredients(true)
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

    fun showRemainedIngredients(isAdditionalPurchase: Boolean = false) {
        println("[🍳] Remained ingredients:")
        val ingredientCountMap = mutableMapOf<String, Int>()
        for (ingredient in ingredients) {
            if (isAdditionalPurchase && !ingredient.isEnableAdditionalPurchase) {
                continue
            }
            ingredientCountMap[ingredient.name] = ingredientCountMap.getOrDefault(ingredient.name, 0) + 1
        }
        for (ingredientCount in ingredientCountMap) {
            println("- ${ingredientEmojiMap[ingredientCount.key]} ${ingredientCount.key} (Count: ${ingredientCount.value}), Price: ₩${ingredients.firstOrNull { it.name == ingredientCount.key }?.price ?: 0})")
        }
    }

    fun showRemainedSides() {
        println("[🍟] Remained sides:")
        val sideCountMap = mutableMapOf<String, Int>()
        for (side in sides) {
            sideCountMap[side.name] = sideCountMap.getOrDefault(side.name, 0) + 1
        }
        for (sideCount in sideCountMap) {
            println("- ${sideEmojiMap[sideCount.key]} ${sideCount.key} (Count: ${sideCount.value}), Price: ₩${sides.firstOrNull { it.name == sideCount.key }?.price ?: 0})")
        }
    }
    
    fun showRemainedAssets() {
        println("[💵] Current inserted money: ₩$insertedMoney")
        showRemainedIngredients()
        showRemainedSides()
    }

    fun showAvailableMenu() {
        println("\n" + "=".repeat(delimeterRepeat))
        showRemainedAssets()
        println("[🔴] Current available buttons as below:")
        for (index in buttons.indices) {
            if (!buttons[index].isActive) continue
            val menu = buttons[index].menu
            if (menu is Pizza) {
                println("$index. [PIZZA] ${menuEmojiMap["Pizza"]} ${menu.name} - Price: ₩${menu.price} (Ingredients: ${menu.ingredients.joinToString(", ") { it.name }})")
            } else if (menu is Beverage) {
                println("$index. [BEVERAGE] ${beverageEmojiMap[menu.name]}️ ${menu.name} - Price: ₩${menu.price}")
            } else if (menu is Side) {
                println("$index. [SIDE DISH] ${sideEmojiMap[menu.name]} ${menu.name} - Price: ₩${menu.price}")
            } else if (menu is Set) {
                println("$index. [SET MENU] ${menuEmojiMap["Set"]}️ ${menu.name} - Price: ₩${menu.price} (Menus: ${menu.menus.joinToString(", ") { it.name }})")
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
            return -2 // Indicating exit
        }
        try {
            val index = input.toInt()
            if (index < 0 || index >= buttons.size || !buttons[index].isActive) {
                println("[❌] Invalid button index. Please try again.")
                return -1
            }
            return index
        } catch (e: NumberFormatException) {
            println("[❌] Invalid input. Please enter a valid number.")
            return -1
        }
    }
}