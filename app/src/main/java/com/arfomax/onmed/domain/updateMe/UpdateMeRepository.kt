package com.arfomax.onmed.domain.updateMe

import com.arfomax.onmed.data.network.updateMe.UpdateMeModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface UpdateMeRepository {

    suspend fun updateMe(updateMeModel: UpdateMeModel) : Flow<BaseResult<String, String>>

}