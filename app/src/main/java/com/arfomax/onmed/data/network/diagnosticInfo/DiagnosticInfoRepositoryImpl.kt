package com.arfomax.onmed.data.network.diagnosticInfo

import android.util.Log
import com.arfomax.onmed.data.network.diagnosticInfo.model.DiagnosticInfoModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.queuesForDoctor.QueuesForDoctorRepository
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.diagnosticInfo.DiagnosticInfoRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiagnosticInfoRepositoryImpl @Inject constructor(private val diagnosticInfoApi: DiagnosticInfoApi) :
    DiagnosticInfoRepository {
    override suspend fun getDiagnosticInfo(diagnosticId: Int): Flow<BaseResult<DiagnosticInfoModel, String>> {
        return flow {
            try {
                val response = diagnosticInfoApi.getDiagnosticInfo(diagnosticId)
                if (response.isSuccessful) {
                    response.body()?.let { emit(BaseResult.Success(it)) }
                }
                else {
                    Log.e("DiagnosticInfoRepositoryImpl", "getDiagnosticInfo errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("DiagnosticInfoRepositoryImpl", "getDiagnosticInfo throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}