package com.arfomax.onmed.domain.addQueueForInspection

import com.arfomax.onmed.data.network.addQueueForInspection.AddQueueForInspectionModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface AddQueueForInspectionRepository {

    suspend fun addQueue(addQueueForInspectionModel: AddQueueForInspectionModel): Flow<BaseResult<String, String>>

}