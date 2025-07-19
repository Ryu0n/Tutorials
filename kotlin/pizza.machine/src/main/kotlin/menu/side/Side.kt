package org.example.menu.side

import org.example.menu.Menu

open class Side(
    override val name: String,
    override val price: Int,
) : Menu(name, price)