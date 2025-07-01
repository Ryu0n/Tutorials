package org.example.machine

import org.example.machine.listener.ButtonEventListener
import org.example.menu.Menu

class Button(
    val menu: Menu,
    override var isActive: Boolean = false,
) : ButtonEventListener {
    fun isMet(insertedMoney: Int): Boolean {
        return insertedMoney >= menu.price
    }
}