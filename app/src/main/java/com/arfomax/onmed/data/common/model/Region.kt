package com.arfomax.onmed.data.common.model


import com.arfomax.onmed.domain.regionsAndDistricts.model.DistrictModel
import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_oz")
    val nameOz: String,
    @SerializedName("name_ru")
    val nameRu: String,
    @SerializedName("name_uz")
    val nameUz: String,
    @SerializedName("region_districts")
    val regionDistricts: List<District>
)