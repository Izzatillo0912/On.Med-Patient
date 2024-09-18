package com.arfomax.onmed.data.network.patientCreate


import com.google.gson.annotations.SerializedName

data class PatientCreateModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("district")
    val district: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("region")
    val region: Int
)