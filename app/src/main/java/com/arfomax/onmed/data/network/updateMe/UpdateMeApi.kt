package com.arfomax.onmed.data.network.updateMe

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface UpdateMeApi {

    @PUT("patient/update/")
    suspend fun update(
        @Body updateMeModel: UpdateMeModel) : Response<ResponseModel>

}