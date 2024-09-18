package com.arfomax.onmed.data.network.queueForDoctor.model


import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.google.gson.annotations.SerializedName

data class QueueModel(
    val id : Int,
    val doctor : DoctorInfoModel,
    @SerializedName("date")
    val date: String,
    @SerializedName("patient")
    val patientId: Int?,
    @SerializedName("diagnostics_inspection")
    val diagnosticsInspection: Int,
    @SerializedName("fio")
    val fio: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("promo_code")
    val promoCode: String,
    @SerializedName("queue_number")
    val queueNumber: Int,
    @SerializedName("queue_type")
    val queueType: String,
    @SerializedName("status")
    val status: String
)