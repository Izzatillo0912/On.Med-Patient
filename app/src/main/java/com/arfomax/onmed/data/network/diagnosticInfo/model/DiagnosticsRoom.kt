package com.arfomax.onmed.data.network.diagnosticInfo.model


import com.google.gson.annotations.SerializedName

data class DiagnosticsRoom(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("room_files")
    val roomFiles: List<RoomFile>
)