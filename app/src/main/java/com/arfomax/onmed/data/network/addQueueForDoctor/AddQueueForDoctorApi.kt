package com.arfomax.onmed.data.network.addQueueForDoctor

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddQueueForDoctorApi {

    @POST("patient/doctor/own_queues/create/")
    suspend fun addQueue(
        @Body addQueueForDoctorModel: AddQueueForDoctorModel
    ) : Response<ResponseModel>

    @POST("patient/doctor/own_queues/create/")
    suspend fun addQueueNoPromo(
        @Body addQueueForDoctorNoPromoModel: AddQueueForDoctorNoPromoModel
    ) : Response<ResponseModel>
}