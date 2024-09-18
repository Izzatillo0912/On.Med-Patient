package com.arfomax.onmed.domain.regionsAndDistricts.model

data class RegionsAndDistrictsModel(
    val nameUz : String,
    val id : Int,
    val districts : ArrayList<DistrictModel>
)
