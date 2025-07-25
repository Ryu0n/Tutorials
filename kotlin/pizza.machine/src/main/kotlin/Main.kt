package org.example

import org.example.machine.Button
import org.example.machine.PizzaMachine
import org.example.menu.beverage.Beer
import org.example.menu.beverage.Coffee
import org.example.menu.beverage.Cola
import org.example.menu.pizza.CheesePepperoniPizza
import org.example.menu.pizza.CheesePizza
import org.example.menu.pizza.PepperoniPizza
import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Ingredient
import org.example.menu.ingredient.Pepperoni
import org.example.menu.set.CheeseCoffeeSaladSet
import org.example.menu.set.CheeseColaPotatoSet
import org.example.menu.set.PepperoniBeerPotatoSet
import org.example.menu.side.CheeseBall
import org.example.menu.side.HotWing
import org.example.menu.side.Potato
import org.example.menu.side.Salad
import org.example.menu.side.Side
import org.example.promotion.DoubleCheesePromotion
import org.example.promotion.Promotion
import java.time.LocalDateTime

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
fun main() {
    val ingredients = mutableListOf<Ingredient>()
        .apply { addAll(List(20) { Dough() }) }
        .apply { addAll(List(20) { Cheese() }) }
        .apply { addAll(List(20) { Pepperoni() }) }
    val sides = mutableListOf<Side>()
        .apply { addAll(List(20) { Potato() }) }
        .apply { addAll(List(20) { Salad() }) }
        .apply { addAll(List(20) { HotWing() }) }
        .apply { addAll(List(20) { CheeseBall() }) }
    val buttons = mutableListOf(
        Button(menu = CheeseColaPotatoSet()),
        Button(menu = CheeseCoffeeSaladSet()),
        Button(menu = PepperoniBeerPotatoSet()),
        Button(menu = CheesePizza()),
        Button(menu = PepperoniPizza()),
        Button(menu = CheesePepperoniPizza()),
        Button(menu = Potato()),
        Button(menu = Salad()),
        Button(menu = Cola()),
        Button(menu = Coffee()),
        Button(menu = Beer()),
    )
    val promotions: MutableList<Promotion> = mutableListOf(
        DoubleCheesePromotion(
            startDate = LocalDateTime.of(2025, 7, 1, 0, 0),
            period = 30,
        ),
    )
    val machine = PizzaMachine(
        buttons = buttons,
        ingredients = ingredients,
        promotions=promotions,
        sides = sides,
    )

    machine.showAvailableMenu()
    while (true) {
        machine.onMoneyInserted()
        machine.showAvailableMenu()
        val index = machine.onButtonSelected()
        if (index == -1) {
            continue
        }
        if (index == -2) {
            break
        }
        machine.buttons[index].onPressed()
    }
}