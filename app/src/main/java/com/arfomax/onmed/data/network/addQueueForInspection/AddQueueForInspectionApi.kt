package com.arfomax.onmed.data.network.addQueueForInspection

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddQueueForInspectionApi {

    @POST("patient/diagnostics/inspection/queues/create/")
    suspend fun addQueue(
        @Body addQueueForInspectionModel: AddQueueForInspectionModel
    ) : Response<ResponseModel>

    @POST("patient/diagnostics/inspection/queues/create/")
    suspend fun addQueueNoPromo(
        @Body addQueueForInspectionNoPromoModel: AddQueueForInspectionNoPromoModel
    ) : Response<ResponseModel>

}