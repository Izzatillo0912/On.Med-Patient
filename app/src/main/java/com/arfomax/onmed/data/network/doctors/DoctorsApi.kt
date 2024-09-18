package com.arfomax.onmed.data.network.doctors

import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DoctorsApi {

    @GET("patient/doctor/all/")
    suspend fun getDoctors(
        @Query("search") search : String
    ) : Response<ArrayList<DoctorInfoModel>>
}