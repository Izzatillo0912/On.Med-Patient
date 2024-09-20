package com.arfomax.onmed.data.network.queueForInspections

import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QueueForInspectionsApi {

    @GET("patient/diagnostics/inspection/queues/")
    suspend fun getQueuesForInspections(
        @Query("from_date") fromDate : String,
        @Query("to_date") toDate : String,
        @Query("inspection") inspectionId : Int,
    ) : Response<QueuesModel>

}