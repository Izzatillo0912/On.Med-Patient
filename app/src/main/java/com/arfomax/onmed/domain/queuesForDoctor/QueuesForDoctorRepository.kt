package com.arfomax.onmed.domain.queuesForDoctor

import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface QueuesForDoctorRepository {

    suspend fun getQueuesForDoctor(date : String, doctorId : Int) : Flow<BaseResult<QueuesModel, String>>

}