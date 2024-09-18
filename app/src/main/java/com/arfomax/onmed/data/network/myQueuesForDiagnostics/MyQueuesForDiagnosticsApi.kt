package com.arfomax.onmed.data.network.myQueuesForDiagnostics

import com.arfomax.onmed.data.common.model.PagingModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import retrofit2.Response
import retrofit2.http.GET

interface MyQueuesForDiagnosticsApi {

    @GET("patient/diagnostics/inspection/queues/all/")
    suspend fun getMyQueuesForDiagnostics() : Response<PagingModel<MyQueuesForDiagnosticsModel>>

}