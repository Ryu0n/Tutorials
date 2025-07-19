package org.example.promotion

import java.time.LocalDateTime

import org.example.menu.ingredient.Ingredient
import org.example.menu.pizza.Pizza

interface Promotion {
    val name: String
    val startDate: LocalDateTime
    val period: Int
    val probability: Int

    fun isActive(): Boolean {
        val now = LocalDateTime.now()
        val endDate = startDate.plusDays(period.toLong())
        return now.isAfter(startDate) && now.isBefore(endDate)
    }

    fun isApplicable(pizza: Pizza, remainedIngredients: List<Ingredient>): Boolean

    fun apply(pizza: Pizza, remainedIngredients: MutableList<Ingredient>): List<Ingredient>
}