package com.arfomax.onmed.presentation.utils.dateForDoctor

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

object GetCurrentDayAndFourthMonthEndDay {

    @SuppressLint("SimpleDateFormat")
    fun get() : Map<Int, String> {
        val today = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDay = dateFormat.format(today.time)

        // 4 oydan keyingi sanani olish
        val calendarFourMonthsLater = today.clone() as Calendar
        calendarFourMonthsLater.add(Calendar.MONTH, 4)

        // 4 oydan keyingi oyning oxirgi kunini olish
        calendarFourMonthsLater.set(Calendar.DAY_OF_MONTH, calendarFourMonthsLater.getActualMaximum(Calendar.DAY_OF_MONTH))

        // Natijalarni formatlash
        val fourthMonthEndDay = dateFormat.format(calendarFourMonthsLater.time)

        return mapOf(1 to currentDay, 2 to fourthMonthEndDay)
    }

}