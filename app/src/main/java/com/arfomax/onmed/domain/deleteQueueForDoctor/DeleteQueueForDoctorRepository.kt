package com.arfomax.onmed.domain.deleteQueueForDoctor

import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface DeleteQueueForDoctorRepository {

    suspend fun deleteQueue(queueId : Int) : Flow<BaseResult<String, String>>

}