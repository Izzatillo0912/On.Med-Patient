package com.arfomax.onmed.presentation.utils.dateForDoctor

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object GetWorkingDaysForSpinner {

    fun formatDates(dateList: List<String>): List<String> {
        // Sana formatlash uchun SimpleDateFormat yaratamiz
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        //oy nomlarini kiritamiz
        val uzbekMonths = listOf(
            "yanvar", "fevral", "mart", "aprel", "may", "iyun",
            "iyul", "avgust", "sentabr", "oktabr", "noyabr", "dekabr"
        )

        return dateList.map { dateStr ->
            try {
                // Sana stringni Date obyektiga aylantiramiz
                val date = inputFormat.parse(dateStr)

                // Kunni va oyni olish uchun Calendar-dan foydalanamiz
                val calendar = Calendar.getInstance().apply { time = date!! }
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendar.get(Calendar.MONTH)

                // Sana formatini o'zgartirib, oy nomini qo'shamiz
                "$day - ${uzbekMonths[month]} kunida navbatlar"
            } catch (e: Exception) {
                // Agar format noto'g'ri bo'lsa, asl stringni qaytaramiz
                dateStr
            }
        }
    }

    fun parseDate(spinnerDay: String): String {

        val day = spinnerDay.replace("-","").split("  "," ")
        return GetDateFormat.getDateFromMonthAndWorkDay(
            day[1].replaceFirstChar { it.uppercase() }, day[0]
        )
    }

}