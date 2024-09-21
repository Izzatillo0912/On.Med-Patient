package com.arfomax.onmed.presentation.utils.dateForInspection

object DateFormatToSpinner {

    fun getMonthName(date : String) : String {
        val index = date.drop(5).take(2).toInt()
        val monthMap = mapOf(1 to "Yanvar", 2 to "Fevral", 3 to "Mart", 4 to "Aprel",
            5 to "May", 6 to "Iyun", 7 to "Iyul", 8 to "Avgust", 9 to "Sentyabr",
            10 to "Oktyabr", 11 to "Noyabr", 12 to "Dekabr")
        return monthMap[index].toString()
    }

    fun day(date : String) : String {
        return date.drop(8) + "-kuni navbatlar"
    }

}