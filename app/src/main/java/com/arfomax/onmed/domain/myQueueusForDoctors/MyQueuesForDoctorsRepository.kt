package com.arfomax.onmed.domain.myQueueusForDoctors

import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface MyQueuesForDoctorsRepository {

    suspend fun myQueuesForDoctors() : Flow<BaseResult<QueuesModel, String>>

}