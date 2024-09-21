package com.arfomax.onmed.presentation.utils.dateForDoctor

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object GetDateFormat {

    fun getDateFromMonthAndWorkDay(monthName: String, workDay: String): String {

        val calendar = Calendar.getInstance()

        val monthNames = arrayOf("Yanvar", "Fevral", "Mart", "Aprel", "May", "Iyun", "Iyul", "Avgust", "Sentyabr", "Oktyabr", "Noyabr", "Dekabr")
        val monthIndex = monthNames.indexOf(monthName.capitalize(Locale.getDefault()))

        val currentMonth = calendar.get(Calendar.MONTH)
        val year = if (monthIndex < currentMonth) calendar.get(Calendar.YEAR) + 1 else calendar.get(Calendar.YEAR)

        val day = workDay.split("-")[0].toInt()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthIndex)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

}