package com.arfomax.onmed.data.network.myQueuesForDiagnostics

import android.util.Log
import com.arfomax.onmed.data.common.model.PagingModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.myQueueusForDiagnostics.MyQueuesForDiagnosticsRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyQueuesForDiagnosticsRepositoryImpl @Inject constructor(
    private val myQueuesForDiagnosticsApi: MyQueuesForDiagnosticsApi) : MyQueuesForDiagnosticsRepository {
    override suspend fun myQueuesForDiagnostics(): Flow<BaseResult<PagingModel<MyQueuesForDiagnosticsModel>, String>> {
        return flow {
            try {
                val response = myQueuesForDiagnosticsApi.getMyQueuesForDiagnostics()
                if (response.isSuccessful) {
                    response.body()?.let { emit(BaseResult.Success(it)) }
                }
                else {
                    Log.e("MyQueuesForDiagnosticsRepositoryImpl", "myQueuesForDiagnostics errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("MyQueuesForDiagnosticsRepositoryImpl", "myQueuesForDiagnostics throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}