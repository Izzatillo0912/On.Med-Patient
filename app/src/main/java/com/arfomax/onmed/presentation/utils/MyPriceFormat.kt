package com.arfomax.onmed.presentation.utils

import java.text.DecimalFormat
import java.text.NumberFormat

object MyPriceFormat {

    fun formattedVolume(volume : Double) : String{
        val formatter: NumberFormat = DecimalFormat("#,###.##")
        return formatter.format(volume)
    }
}