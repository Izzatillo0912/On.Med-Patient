package com.arfomax.onmed.data.network.myQueuesForDoctors

import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import retrofit2.Response
import retrofit2.http.GET

interface MyQueuesForDoctorsApi {

    @GET("patient/doctor/own_queues/all/")
    suspend fun getMyQueuesForDoctors() : Response<QueuesModel>

}