package com.arfomax.onmed.data.network.patientCreate

import android.util.Log
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.login.LoginRepository
import com.arfomax.onmed.domain.patientCreate.PatientCreateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientCreateRepositoryImpl @Inject constructor(private val patientCreateApi: PatientCreateApi) : PatientCreateRepository {
    override suspend fun patientCreate(patientCreateModel: PatientCreateModel): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = patientCreateApi.patientCreate(patientCreateModel)
                if (response.isSuccessful) emit(BaseResult.Success(""))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("PatientCreateRepositoryImpl", "patientCreate error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch(t : Throwable) {
                Log.e("PatientCreateRepositoryImpl", "patientCreate throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}