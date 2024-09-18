package com.arfomax.onmed.data.network.updateQueueDateForDoctor

import android.util.Log
import com.arfomax.onmed.domain.updateQueueDateForDoctor.UpdateQueueDateForDoctorRepository
import com.arfomax.onmed.data.network.updateQueueDateForDoctor.UpdateQueueDateForDoctorApi
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateQueueDateForDoctorRepositoryImpl @Inject constructor(
    private val updateQueueDateForDoctorApi: UpdateQueueDateForDoctorApi
) : UpdateQueueDateForDoctorRepository {
    override suspend fun updateQueueDate(queueId : Int, date : String): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = updateQueueDateForDoctorApi.updateDate(queueId, date)
                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_CHANGED))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("UpdateQueueDateForDoctorRepositoryImpl", "updateQueueDate error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch (t : Throwable) {
                Log.e("UpdateQueueDateForDoctorRepositoryImpl", "updateQueueDate throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}