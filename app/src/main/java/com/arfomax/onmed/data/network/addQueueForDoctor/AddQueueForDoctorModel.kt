package com.arfomax.onmed.data.network.addQueueForDoctor


import com.google.gson.annotations.SerializedName

data class AddQueueForDoctorModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("doctor")
    val doctor: Int,
    @SerializedName("fio")
    val fio: String,
    @SerializedName("promo_code")
    val promoCode: String,
    @SerializedName("queue_type")
    val queueType: String
)