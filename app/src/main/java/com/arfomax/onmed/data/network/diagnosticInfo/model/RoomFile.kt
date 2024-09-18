package com.arfomax.onmed.data.network.diagnosticInfo.model


import com.google.gson.annotations.SerializedName

data class RoomFile(
    @SerializedName("file")
    val file: String,
    @SerializedName("id")
    val id: Int
)