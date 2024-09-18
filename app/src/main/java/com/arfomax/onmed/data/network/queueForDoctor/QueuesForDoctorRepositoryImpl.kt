package com.arfomax.onmed.data.network.queueForDoctor

import android.util.Log
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.queuesForDoctor.QueuesForDoctorRepository
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QueuesForDoctorRepositoryImpl @Inject constructor(private val queueForDoctorApi: QueueForDoctorApi) :
    QueuesForDoctorRepository {
    override suspend fun getQueuesForDoctor(date: String, doctorId : Int): Flow<BaseResult<QueuesModel, String>> {
        return flow {
            try {
                val response = queueForDoctorApi.getQueuesForDoctor(date, doctorId)
                if (response.isSuccessful) {
                    response.body()?.let { emit(BaseResult.Success(it)) }
                }
                else {
                    Log.e("QueuesForDoctorRepositoryImpl", "getQueuesForDoctor errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("QueuesForDoctorRepositoryImpl", "getQueuesForDoctor throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}