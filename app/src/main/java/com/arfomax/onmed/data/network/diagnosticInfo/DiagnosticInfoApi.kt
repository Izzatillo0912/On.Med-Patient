package com.arfomax.onmed.data.network.diagnosticInfo

import com.arfomax.onmed.data.network.diagnosticInfo.model.DiagnosticInfoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiagnosticInfoApi {

    @GET("patient/diagnostics/")
    suspend fun getDiagnosticInfo(
        @Query("pk") diagnosticId : Int
    ) : Response<DiagnosticInfoModel>

}