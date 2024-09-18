package com.arfomax.onmed.data.network.smsVerification

import android.util.Log
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import com.arfomax.onmed.data.network.smsVerification.model.SmsVerificationRequestModel
import com.arfomax.onmed.data.network.smsVerification.model.SmsVerificationResponseModel
import com.arfomax.onmed.domain.smsVerification.SmsVerificationRepository
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SmsVerificationRepositoryImpl @Inject constructor(private val smsVerificationApi: SmsVerificationApi) :
    SmsVerificationRepository {
    override suspend fun smsVerification(phoneNumber : String, code : Int): Flow<BaseResult<SmsVerificationResponseModel, String>> {
        return flow {
            try {
                val response = smsVerificationApi.login(SmsVerificationRequestModel(code, phoneNumber))
                if (response.isSuccessful) { response.body()?.let { emit(BaseResult.Success(it)) } }
                else {
                    if(response.code() == 404) emit(BaseResult.Error("Sizning malumotalaringiz topilmadi!"))
                    else {
                        emit(BaseResult.Error(ActionResultMessage.ERROR))
                        Log.e("SmsVerificationRepositoryImpl", "smsVerification error : " +
                                "${response.errorBody()?.string()} / code = ${response.code()}")
                    }
                }
            }
            catch(t : Throwable) {
                Log.e("SmsVerificationRepositoryImpl", "auth throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}