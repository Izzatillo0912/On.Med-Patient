package com.arfomax.onmed.data.network.updateQueueDateForDoctor

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.Query

interface UpdateQueueDateForDoctorApi {

    @PUT("patient/doctor/own_queues/update/date/")
    suspend fun updateDate(
        @Query("pk") queueId : Int,
        @Query("date") date : String,
    ) : Response<ResponseModel>

}