package com.arfomax.onmed.presentation.utils.dateForInspection

import java.util.Calendar

object GetMonths {


    fun generateNextThreeMonths(): List<String> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val monthList = mutableListOf<String>()

        val uzbekMonthNames = listOf(
            "Yanvar", "Fevral", "Mart", "Aprel", "May", "Iyun",
            "Iyul", "Avgust", "Sentyabr", "Oktyabr", "Noyabr", "Dekabr"
        )

        repeat(3) {
            val monthIndex = calendar.get(Calendar.MONTH)
            val monthName = uzbekMonthNames[monthIndex]
            val year = calendar.get(Calendar.YEAR)

            if (year > currentYear) {
                monthList.add("$monthName $year")
            } else {
                monthList.add(monthName)
            }

            calendar.add(Calendar.MONTH, 1)
        }

        return monthList
    }

}