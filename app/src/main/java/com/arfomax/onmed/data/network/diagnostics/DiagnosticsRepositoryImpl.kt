package com.arfomax.onmed.data.network.diagnostics

import android.util.Log
import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticsModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.diagnostics.DiagnosticsRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiagnosticsRepositoryImpl @Inject constructor(private val diagnosticsApi: DiagnosticsApi) : DiagnosticsRepository{

    override suspend fun getAllDiagnostics(search : String): Flow<BaseResult<ArrayList<DiagnosticsModel>, String>> {
        return flow {
            try {
                val response = diagnosticsApi.getAllDiagnostics(search)
                if (response.isSuccessful) response.body()?.let { data -> emit(BaseResult.Success(data)) }
                else {
                    Log.e("DiagnosticsRepositoryImpl", "getAllDiagnostics errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("DiagnosticsRepositoryImpl", "getAllDiagnostics throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}