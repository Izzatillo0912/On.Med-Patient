package com.arfomax.onmed.data.network.doctors

import android.util.Log
import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.doctors.DoctorsRepository
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoctorsRepositoryImpl @Inject constructor(private val doctorsApi: DoctorsApi) : DoctorsRepository{

    override suspend fun doctors(search : String): Flow<BaseResult<ArrayList<DoctorInfoModel>, String>> {
        return flow {
            try {
                val response = doctorsApi.getDoctors(search)
                if (response.isSuccessful) response.body()?.let { data-> emit(BaseResult.Success(data)) }
                else {
                    Log.e("DoctorsRepositoryImpl", "doctors errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("DoctorsRepositoryImpl", "doctors throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}