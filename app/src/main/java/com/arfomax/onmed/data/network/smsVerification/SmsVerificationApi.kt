package com.arfomax.onmed.data.network.smsVerification

import com.arfomax.onmed.data.network.smsVerification.model.SmsVerificationRequestModel
import com.arfomax.onmed.data.network.smsVerification.model.SmsVerificationResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsVerificationApi {

    @POST("patient/sms/verification/")
    suspend fun login(
        @Body smsVerificationRequestModel: SmsVerificationRequestModel
    ) : Response<SmsVerificationResponseModel>

}