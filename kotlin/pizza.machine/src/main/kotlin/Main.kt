package org.example

import org.example.machine.Button
import org.example.machine.PizzaMachine
import org.example.menu.CheesePepperoniPizza
import org.example.menu.CheesePizza
import org.example.menu.PepperoniPizza
import org.example.menu.ingredient.Cheese
import org.example.menu.ingredient.Dough
import org.example.menu.ingredient.Pepperoni

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
fun main() {
    val ingredients = mutableListOf(
        Dough(),
        Dough(),
        Dough(),
        Dough(),
        Dough(),
        Cheese(),
        Cheese(),
        Cheese(),
        Cheese(),
        Pepperoni(),
        Pepperoni(),
    )
    val machine = PizzaMachine(
        buttons = mutableListOf(
            Button(
                menu = CheesePizza()
            ),
            Button(
                menu = PepperoniPizza()
            ),
            Button(
                menu = CheesePepperoniPizza()
            )
        ),
        ingredients = ingredients,
    )

    machine.showAvailableMenu()
    while (true) {
        machine.insertMoney()
        machine.showAvailableMenu()
        val index = machine.selectButton()
        if (index == -1) {
            break
        }
        machine.pressButton(index)
    }
}