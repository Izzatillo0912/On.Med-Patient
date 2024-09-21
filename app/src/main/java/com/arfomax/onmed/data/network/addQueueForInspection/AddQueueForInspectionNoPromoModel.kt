package com.arfomax.onmed.data.network.addQueueForInspection


import com.google.gson.annotations.SerializedName

data class AddQueueForInspectionNoPromoModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("diagnostics_inspection")
    val diagnosticsInspection: Int,
    @SerializedName("fio")
    val fio: String,
    @SerializedName("queue_type")
    val queueType: String
)