package com.arfomax.onmed.data.network.getWorkDays


import com.google.gson.annotations.SerializedName

data class GetWorkDay(
    @SerializedName("id")
    val id: Int,
    @SerializedName("work_day")
    val workDay: String
)