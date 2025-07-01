package org.example.machine

class PizzaMachine(
    val delimeterRepeat: Int = 50,
    var insertedMoney: Int = 0,
    val buttons: MutableList<Button>,
) {
    fun showMenu(): Unit {
        println("\n" + "=".repeat(delimeterRepeat))
        println("Welcome to the Pizza Machine!")
        println("Current inserted money: $$insertedMoney")
        for (button in buttons) {
            if (button.isActive) {
                println("${button.menu.name} - Price: $${button.menu.price} (Available)")
            } else {
                println("${button.menu.name} - Price: $${button.menu.price} (Not Available)")
            }
        }
    }

    fun showAvailableMenu(): Unit {
        println("\n" + "=".repeat(delimeterRepeat))
        println("You can purchase $$insertedMoney worth of pizza.")
        println("Current available buttons as below:")
        for (index in buttons.indices) {
            if (!buttons[index].isActive) continue
            println("$index. ${buttons[index].menu.name} - Price: $${buttons[index].menu.price}")
        }
    }

    fun selectButton(): Int {
        println("\n" + "=".repeat(delimeterRepeat))
        print("Please select a button by index (or type 'exit' to quit): ")
        val input = readLine()
        if (input == null || input.lowercase() == "exit") {
            println("Exiting the machine. Thank you!")
            return -1 // Indicating exit
        }
        return input.toInt()
    }

    fun refreshButtons() {
        for (button in buttons) {
            if (button.isMet(insertedMoney)) {
                button.onButtonActivation()
            } else {
                button.onButtonDeactivation()
            }
        }
    }

    fun insertMoney() {
        println("\n" + "=".repeat(delimeterRepeat))
        println("Insert money ($):")
        val money = readLine()?.toIntOrNull()

        money?.let {
            if (it < 0) {
                println("Please insert a valid amount of money.")
            }
        }

        insertedMoney += money ?: 0
        refreshButtons()
    }

    fun pressButton(index: Int) {
        if (index < 0 || index >= buttons.size) {
            println("Invalid button index.")
            return
        }

        val button = buttons[index]
        if (!button.isActive) {
            println("Button ${button.menu.name} is not active. Please insert more money.")
            return
        }

        insertedMoney -= button.menu.price
        println("You have purchased ${button.menu.name}. Remaining money: $$insertedMoney")
        refreshButtons()
    }
}