package com.arfomax.onmed.data.network.addQueueForInspection

import android.util.Log
import com.arfomax.onmed.domain.addQueueForInspection.AddQueueForInspectionRepository
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddQueueForInspectionRepositoryImpl @Inject constructor(
    private val addQueueForInspectionApi: AddQueueForInspectionApi) : AddQueueForInspectionRepository {
    override suspend fun addQueue(addQueueForInspectionModel: AddQueueForInspectionModel): Flow<BaseResult<String, String>> {
        return flow {
            try {
                val response = if (addQueueForInspectionModel.promoCode.isNotEmpty()) {
                    addQueueForInspectionApi.addQueue(addQueueForInspectionModel)
                } else {
                    addQueueForInspectionApi.addQueueNoPromo(AddQueueForInspectionNoPromoModel(
                        addQueueForInspectionModel.date, addQueueForInspectionModel.diagnosticsInspection,
                        addQueueForInspectionModel.fio, "online"))
                }
                if (response.isSuccessful) emit(BaseResult.Success(ActionResultMessage.SUCCESS_ADD))
                else {
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                    Log.e("AddQueueForInspectionRepositoryImpl", "addQueue error : " +
                            "${response.errorBody()?.string()} / code = ${response.code()}")
                }
            }
            catch (t : Throwable) {
                Log.e("AddQueueForInspectionRepositoryImpl", "addQueue throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}