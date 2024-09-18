package com.arfomax.onmed.data.network.deleteQueueForDoctor

import android.util.Log
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.deleteQueueForDoctor.DeleteQueueForDoctorRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteQueueForDoctorRepositoryImpl @Inject constructor(
    private val deleteQueueForDoctorApi: DeleteQueueForDoctorApi) : DeleteQueueForDoctorRepository {
    override suspend fun deleteQueue(queueId : Int): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = deleteQueueForDoctorApi.deleteQueue(queueId)
                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_REMOVE))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("DeleteQueueForDoctorRepositoryImpl", "deleteQueueForDoctor error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch (t : Throwable) {
                Log.e("DeleteQueueForDoctorRepositoryImpl", "deleteQueueForDoctor throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}