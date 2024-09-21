package com.arfomax.onmed.data.network.deleteQueueForInspection

import android.util.Log
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.deleteQueueForInspection.DeleteQueueForInspectionRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteQueueForInspectionRepositoryImpl @Inject constructor(
    private val deleteQueueForInspectionApi: DeleteQueueForInspectionApi
) : DeleteQueueForInspectionRepository {
    override suspend fun deleteQueue(queueId : Int): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = deleteQueueForInspectionApi.deleteQueue(queueId)
                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_REMOVE))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("DeleteQueueForInspectionRepositoryImpl", "deleteQueue error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch (t : Throwable) {
                Log.e("DeleteQueueForInspectionRepositoryImpl", "deleteQueue throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}