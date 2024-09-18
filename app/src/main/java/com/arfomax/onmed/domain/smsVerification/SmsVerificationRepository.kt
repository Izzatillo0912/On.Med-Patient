package com.arfomax.onmed.domain.smsVerification

import com.arfomax.onmed.data.network.smsVerification.model.SmsVerificationResponseModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface SmsVerificationRepository {

    suspend fun smsVerification(phoneNumber : String, code : Int) : Flow<BaseResult<SmsVerificationResponseModel, String>>

}