package com.arfomax.onmed.data.network.getWorkDays

import android.util.Log
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.getWorkDays.GetWorkDaysRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorkDaysRepositoryImpl @Inject constructor(private val getWorkDaysApi: GetWorkDaysApi) : GetWorkDaysRepository {
    override suspend fun getWorkDays(doctorId : Int, fromDate : String, toDate : String): Flow<BaseResult<ArrayList<GetWorkDay>, String>> {
        return flow {
            try {
                val response = getWorkDaysApi.getWorkDays(doctorId, fromDate, toDate)
                if (response.isSuccessful) response.body()?.let { emit(BaseResult.Success(it)) }
                else {
                    Log.e("GetWorkDaysRepositoryImpl", "getWorkDays errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("GetWorkDaysRepositoryImpl", "getWorkDays throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}