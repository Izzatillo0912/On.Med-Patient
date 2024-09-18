package com.arfomax.onmed.domain.updateQueueDateForDoctor

import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface UpdateQueueDateForDoctorRepository {

    suspend fun updateQueueDate(queueId : Int, date : String) : Flow<BaseResult<String, String>>

}