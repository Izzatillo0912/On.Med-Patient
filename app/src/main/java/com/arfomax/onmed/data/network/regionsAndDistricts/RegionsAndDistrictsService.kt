package com.arfomax.onmed.data.network.regionsAndDistricts

import com.arfomax.onmed.data.common.model.Region
import retrofit2.Response
import retrofit2.http.GET

interface RegionsAndDistrictsService {

    @GET("patient/region/all/")
    suspend fun getRegionsAndDistricts() : Response<List<Region>>

}