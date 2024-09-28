package com.arfomax.onmed.data.network.updateMe


import com.google.gson.annotations.SerializedName

data class UpdateMeModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("district")
    val district: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("region")
    val region: Int
)