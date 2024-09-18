package com.arfomax.onmed.data.network.getWorkDays

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetWorkDaysApi {

    @GET("patient/doctor/workdays/")
    suspend fun getWorkDays(
        @Query("pk") doctorId : Int,
        @Query("from_date") fromDate : String,
        @Query("to_date") toDate : String
    ) : Response<ArrayList<GetWorkDay>>

}