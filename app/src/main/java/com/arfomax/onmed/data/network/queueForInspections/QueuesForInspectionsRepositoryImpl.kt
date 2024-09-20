package com.arfomax.onmed.data.network.queueForInspections

import android.util.Log
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.queuesForInspections.QueuesForInspectionsRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QueuesForInspectionsRepositoryImpl @Inject constructor(
    private val queueForInspectionsApi: QueueForInspectionsApi
) : QueuesForInspectionsRepository {
    override suspend fun getQueuesForInspection(inspectionId: Int, date: String): Flow<BaseResult<QueuesModel, String>> {
        return flow {
            try {
                val response = queueForInspectionsApi.getQueuesForInspections(date, date, inspectionId)
                if (response.isSuccessful) {
                    response.body()?.let { emit(BaseResult.Success(it)) }
                }
                else {
                    Log.e("QueuesForInspectionsRepositoryImpl", "getQueuesForInspection errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("QueuesForInspectionsRepositoryImpl", "getQueuesForInspection throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}