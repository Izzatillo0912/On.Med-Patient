package com.arfomax.onmed.domain.deleteQueueForInspection

import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface DeleteQueueForInspectionRepository {

    suspend fun deleteQueue(queueId : Int) : Flow<BaseResult<String, String>>

}