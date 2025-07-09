package org.example.machine

import org.example.machine.event.ActivationEvent
import org.example.menu.ingredient.Ingredient

class PizzaMachine(
    val delimeterRepeat: Int = 50,
    var insertedMoney: Int = 0,
    val buttons: MutableList<Button>,
    val ingredients: MutableList<Ingredient>,
) {
    init {
        buttons.forEach { button ->
            button.onPurchaseRequested = { menu ->
                insertedMoney -= button.menu.price
                button.menu.ingredients.forEach { ingredients.remove(it) }
                println("You have purchased ${button.menu.name}.")
                showAvailableAssets()
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

    fun onMoneyInserted() {
        println("\n" + "=".repeat(delimeterRepeat))
        println("Insert money ($):")
        val money = readLine()?.toIntOrNull()

        money?.let {
            if (it < 0) {
                println("Please insert a valid amount of money.")
            }
        }

        insertedMoney += money ?: 0
        refreshButtonStates()
    }

    fun showAvailableAssets(): Unit {
        println("Current inserted money: ₩$insertedMoney")
        println("Available ingredients:")
        for (ingredient in ingredients) {
            println("- ${ingredient.name} (Price: ₩${ingredient.price})")
        }
    }

    fun showAvailableMenu(): Unit {
        println("\n" + "=".repeat(delimeterRepeat))
        showAvailableAssets()
        println("Current available buttons as below:")
        for (index in buttons.indices) {
            if (!buttons[index].isActive) continue
            println("$index. ${buttons[index].menu.name} - Price: ₩${buttons[index].menu.price} (Ingredients: ${buttons[index].menu.ingredients.joinToString(", ") { it.name }})")
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

        println("You have received ₩$insertedMoney :")
        println("$div10000 x ₩10000")
        println("$div5000 x ₩5000")
        println("$div1000 x ₩1000")
        println("$div500 x ₩500")
        println("$div100 x ₩100")
        println("₩$mod100")
    }

    fun onButtonSelected(): Int {
        println("\n" + "=".repeat(delimeterRepeat))
        print("Please select a button by index (or type 'exit' to quit): ")
        val input = readLine()
        if (input == null || input.lowercase() == "exit") {
            returnChange()
            println("Exiting the machine. Thank you!")
            return -1 // Indicating exit
        }
        return input.toInt()
    }
}