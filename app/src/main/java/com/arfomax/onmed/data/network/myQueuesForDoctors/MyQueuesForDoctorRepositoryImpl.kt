package com.arfomax.onmed.data.network.myQueuesForDoctors

import android.util.Log
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.queuesForDoctor.QueuesForDoctorRepository
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.myQueueusForDoctors.MyQueuesForDoctorsRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyQueuesForDoctorRepositoryImpl @Inject constructor(private val myQueuesForDoctorsApi: MyQueuesForDoctorsApi) :
    MyQueuesForDoctorsRepository {
    override suspend fun myQueuesForDoctors(): Flow<BaseResult<QueuesModel, String>> {
        return flow {
            try {
                val response = myQueuesForDoctorsApi.getMyQueuesForDoctors()
                if (response.isSuccessful) {
                    response.body()?.let { emit(BaseResult.Success(it)) }
                }
                else {
                    Log.e("MyQueuesForDoctorRepositoryImpl", "myQueuesForDoctors errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("MyQueuesForDoctorRepositoryImpl", "myQueuesForDoctors throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}