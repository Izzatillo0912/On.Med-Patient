package com.arfomax.onmed.data.network.smsVerification.model


import com.google.gson.annotations.SerializedName

data class SmsVerificationRequestModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("phone")
    val phone: String
)