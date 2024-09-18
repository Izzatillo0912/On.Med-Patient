package com.arfomax.onmed.domain.addQueueForDoctor

import com.arfomax.onmed.data.network.addQueueForDoctor.AddQueueForDoctorModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface AddQueueForDoctorRepository {

    suspend fun addQueue(addQueueForDoctorModel: AddQueueForDoctorModel): Flow<BaseResult<String, String>>

}