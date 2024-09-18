package com.arfomax.onmed.data.network.diagnosticInfo.model


import com.google.gson.annotations.SerializedName

data class Inspection(
    @SerializedName("id")
    val id: Int,
    @SerializedName("info")
    val info: String,
    @SerializedName("name")
    val name: String
)