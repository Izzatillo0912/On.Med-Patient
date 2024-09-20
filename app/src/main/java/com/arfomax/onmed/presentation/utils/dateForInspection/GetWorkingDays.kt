package com.arfomax.onmed.presentation.utils.dateForInspection

import java.util.Calendar
import java.util.GregorianCalendar

object GetWorkingDays {

    fun getDaysInMonth(month: String?, workDay: String?): List<String> {
        val daysList = mutableListOf<String>()

        if (workDay.isNullOrEmpty() || month.isNullOrEmpty() ) return daysList

        val calendar = GregorianCalendar()
        val currentDate = Calendar.getInstance()

        val monthMap = mapOf(
            "Yanvar" to Calendar.JANUARY, "Fevral" to Calendar.FEBRUARY, "Mart" to Calendar.MARCH,
            "Aprel" to Calendar.APRIL, "May" to Calendar.MAY, "Iyun" to Calendar.JUNE,
            "Iyul" to Calendar.JULY, "Avgust" to Calendar.AUGUST, "Sentyabr" to Calendar.SEPTEMBER,
            "Oktyabr" to Calendar.OCTOBER, "Noyabr" to Calendar.NOVEMBER, "Dekabr" to Calendar.DECEMBER
        )

        val dayMap = mapOf(
            "Dushanba" to Calendar.MONDAY, "Seshanba" to Calendar.TUESDAY, "Chorshanba" to Calendar.WEDNESDAY,
            "Payshanba" to Calendar.THURSDAY, "Juma" to Calendar.FRIDAY, "Shanba" to Calendar.SATURDAY,
            "Yakshanba" to Calendar.SUNDAY
        )

        calendar.set(Calendar.MONTH, monthMap[month] ?: throw IllegalArgumentException("Noma'lum oy: $month"))
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val startDay = dayMap[workDay.split(" - ")[0].removeSuffix("dan").trim()]!!
        val endDay = dayMap[workDay.split(" - ")[1].removeSuffix("gacha").trim()]!!

        while (calendar.get(Calendar.MONTH) == monthMap[month]) {
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek in startDay..endDay &&
                (calendar.after(currentDate) || calendar.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH))) {
                daysList.add("${calendar.get(Calendar.DAY_OF_MONTH)}-kuni navbatlar")
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return daysList
    }
}