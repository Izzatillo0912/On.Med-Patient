package com.arfomax.onmed.data.network.myQueuesForDiagnostics


import com.arfomax.onmed.data.network.diagnosticInfo.model.CombineInspection
import com.google.gson.annotations.SerializedName

data class MyQueuesForDiagnosticsModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("diagnostics_name")
    val diagnosticName: String,
    @SerializedName("diagnostics_inspection")
    val diagnosticsInspection: CombineInspection,
    @SerializedName("fio")
    val fio: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("patient")
    val patient: Int,
    @SerializedName("queue_number")
    val queueNumber: Int,
    @SerializedName("status")
    val status: String
)