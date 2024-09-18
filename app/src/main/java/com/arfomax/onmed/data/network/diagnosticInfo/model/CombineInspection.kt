package com.arfomax.onmed.data.network.diagnosticInfo.model


import com.google.gson.annotations.SerializedName

data class CombineInspection(
    @SerializedName("average_time")
    val averageTime: String,
    @SerializedName("diagnostics")
    val diagnostics: Int,
    @SerializedName("end_lunch_time")
    val endLunchTime: String,
    @SerializedName("end_work_time")
    val endWorkTime: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("incentive_price")
    val incentivePrice: Int,
    @SerializedName("info")
    val info: String,
    @SerializedName("inspection")
    val inspection: Inspection,
    @SerializedName("price")
    val price: Int,
    @SerializedName("start_lunch_time")
    val startLunchTime: String,
    @SerializedName("start_work_time")
    val startWorkTime: String,
    @SerializedName("workday")
    val workday: String
)