package com.arfomax.onmed.data.network.doctors.model


import com.google.gson.annotations.SerializedName

data class SpecialityModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("info")
    val info: String,
    @SerializedName("name")
    val name: String
)