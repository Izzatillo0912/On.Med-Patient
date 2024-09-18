package com.arfomax.onmed.data.network.smsVerification.model
import com.google.gson.annotations.SerializedName

data class SmsVerificationResponseModel(
    @SerializedName("access_token")
    val accessToken : String,
    val patient : MeInfoModel
)