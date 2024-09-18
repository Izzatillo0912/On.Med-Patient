package com.arfomax.onmed.data.network.login

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("patient/login/")
    suspend fun login(
        @Body loginRequestModel: LoginRequestModel
    ) : Response<ResponseModel>

}