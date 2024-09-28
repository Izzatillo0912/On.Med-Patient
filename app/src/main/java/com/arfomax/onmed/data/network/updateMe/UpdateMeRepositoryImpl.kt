package com.arfomax.onmed.data.network.updateMe

import android.util.Log
import com.arfomax.onmed.domain.updateQueueDateForDoctor.UpdateQueueDateForDoctorRepository
import com.arfomax.onmed.data.network.updateQueueDateForDoctor.UpdateQueueDateForDoctorApi
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.updateMe.UpdateMeRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateMeRepositoryImpl @Inject constructor(
    private val updateMeApi: UpdateMeApi) : UpdateMeRepository {
    override suspend fun updateMe(updateMeModel: UpdateMeModel): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = updateMeApi.update(updateMeModel)
                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_CHANGED))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("UpdateMeRepositoryImpl", "updateMe error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch (t : Throwable) {
                Log.e("UpdateMeRepositoryImpl", "updateMe throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}