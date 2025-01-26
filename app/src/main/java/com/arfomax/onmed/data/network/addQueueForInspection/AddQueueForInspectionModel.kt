package com.arfomax.onmed.data.network.addQueueForInspection


import com.google.gson.annotations.SerializedName

data class AddQueueForInspectionModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("diagnostics_inspection")
    val diagnosticsInspection: Int,
    @SerializedName("fio")
    val fio: String,
    @SerializedName("from_doctor")
    val fromDoctor: Int,
    @SerializedName("queue_type")
    val queueType: String
)