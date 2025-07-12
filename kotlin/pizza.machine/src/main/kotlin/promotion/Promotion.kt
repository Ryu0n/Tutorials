package org.example.promotion

import java.time.LocalDateTime

interface Promotion {
    val name: String
    val startDate: LocalDateTime
    val period: Int

    fun isActive(): Boolean {
        val now = LocalDateTime.now()
        val endDate = startDate.plusDays(period.toLong())
        return now.isAfter(startDate) && now.isBefore(endDate)
    }
}