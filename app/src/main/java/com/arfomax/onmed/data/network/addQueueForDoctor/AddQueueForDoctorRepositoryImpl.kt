package com.arfomax.onmed.data.network.addQueueForDoctor

import android.util.Log
import com.arfomax.onmed.domain.addQueueForDoctor.AddQueueForDoctorRepository
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddQueueForDoctorRepositoryImpl @Inject constructor(
    private val addQueueForDoctorApi: AddQueueForDoctorApi
) : AddQueueForDoctorRepository {
    override suspend fun addQueue(addQueueForDoctorModel: AddQueueForDoctorModel): Flow<BaseResult<String, String>> {
        return flow {
            try {
                Log.e("AddQueueForDoctorRepositoryImpl", "addQueueForDoctorModel: $addQueueForDoctorModel")
                val response = if (addQueueForDoctorModel.promoCode.isNotEmpty()) {
                    Log.e("AddQueueForDoctorRepositoryImpl", "Promo: $addQueueForDoctorModel")
                    addQueueForDoctorApi.addQueue(addQueueForDoctorModel)
                } else {
                    Log.e("AddQueueForDoctorRepositoryImpl", "noPromo: $addQueueForDoctorModel")
                    addQueueForDoctorApi.addQueueNoPromo(AddQueueForDoctorNoPromoModel(
                        addQueueForDoctorModel.date, addQueueForDoctorModel.doctor,
                        addQueueForDoctorModel.fio, addQueueForDoctorModel.queueType)
                    )
                }

                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_ADD))
                else {
                    if (response.code() == 404) emit(BaseResult.Error("Bunday promo kod mavjud emas1"))
                    else emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("AddQueueForDoctorRepositoryImpl", "addQueue error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch (t : Throwable) {
                Log.e("AddQueueForDoctorRepositoryImpl", "addQueue throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}