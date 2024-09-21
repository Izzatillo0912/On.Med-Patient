package com.arfomax.onmed.data.network.deleteQueueForInspection

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Query

interface DeleteQueueForInspectionApi {

    @DELETE("patient/diagnostics/inspection/queues/delete/")
    suspend fun deleteQueue(
        @Query("pk") queueId : Int
    ) : Response<ResponseModel>

}