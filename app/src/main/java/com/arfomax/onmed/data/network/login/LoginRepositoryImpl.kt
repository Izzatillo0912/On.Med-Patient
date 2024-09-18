package com.arfomax.onmed.data.network.login

import android.util.Log
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun login(phoneNumber : String): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = loginApi.login(LoginRequestModel(phoneNumber))
                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_SEND_NUMBER))
                else if (response.code() == 404) emit(BaseResult.Error("not_found"))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("LoginRepositoryImpl", "login error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch(t : Throwable) {
                Log.e("LoginRepositoryImpl", "login throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}