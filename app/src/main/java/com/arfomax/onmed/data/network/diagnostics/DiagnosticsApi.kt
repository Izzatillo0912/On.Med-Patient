package com.arfomax.onmed.data.network.diagnostics

import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiagnosticsApi {

    @GET("patient/diagnostics/all/")
    suspend fun getAllDiagnostics(
        @Query("search") search : String
    ) : Response<ArrayList<DiagnosticsModel>>

}