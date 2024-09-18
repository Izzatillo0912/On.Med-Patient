package com.arfomax.onmed.data.network.patientCreate

import com.arfomax.onmed.data.common.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PatientCreateApi {

    @POST("patient/create/")
    suspend fun patientCreate(
        @Body patientCreateModel: PatientCreateModel
    ) : Response<ResponseModel>

}