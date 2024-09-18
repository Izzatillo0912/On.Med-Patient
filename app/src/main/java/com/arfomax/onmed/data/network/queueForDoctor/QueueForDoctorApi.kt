package com.arfomax.onmed.data.network.queueForDoctor

import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QueueForDoctorApi {

    @GET("patient/doctor/own_queues/")
    suspend fun getQueuesForDoctor(
        @Query("date") date : String,
        @Query("pk") doctorId : Int,
    ) : Response<QueuesModel>

}