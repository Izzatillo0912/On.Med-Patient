package com.arfomax.onmed.data.network.smsVerification.model


import com.arfomax.onmed.data.common.model.District
import com.arfomax.onmed.data.common.model.Region
import com.google.gson.annotations.SerializedName

data class MeInfoModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("district")
    val district: District,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("region")
    val region: Region
)