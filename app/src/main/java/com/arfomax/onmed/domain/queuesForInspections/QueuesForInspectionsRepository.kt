package com.arfomax.onmed.domain.queuesForInspections

import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface QueuesForInspectionsRepository {

    suspend fun getQueuesForInspection(inspectionId : Int, date : String) : Flow<BaseResult<QueuesModel, String>>

}