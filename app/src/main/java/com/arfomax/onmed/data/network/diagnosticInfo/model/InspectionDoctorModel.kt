package com.arfomax.onmed.data.network.diagnosticInfo.model


import com.arfomax.onmed.data.network.doctors.model.SpecialityModel
import com.google.gson.annotations.SerializedName

data class InspectionDoctorModel(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("speciality")
    val speciality: SpecialityModel
)