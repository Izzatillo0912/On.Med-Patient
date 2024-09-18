package com.arfomax.onmed.data.network.doctors.model


import com.arfomax.onmed.data.common.model.District
import com.arfomax.onmed.data.common.model.Region
import com.google.gson.annotations.SerializedName

data class DoctorInfoModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("district")
    val district: District,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("price")
    val price: Double,
    @SerializedName("region")
    val region: Region,
    @SerializedName("speciality")
    val speciality: SpecialityModel?,
    @SerializedName("description")
    val description: String
)