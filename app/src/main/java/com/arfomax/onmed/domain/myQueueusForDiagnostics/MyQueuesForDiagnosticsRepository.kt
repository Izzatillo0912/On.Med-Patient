package com.arfomax.onmed.domain.myQueueusForDiagnostics

import com.arfomax.onmed.data.common.model.PagingModel
import com.arfomax.onmed.data.network.myQueuesForDiagnostics.MyQueuesForDiagnosticsModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface MyQueuesForDiagnosticsRepository {

    suspend fun myQueuesForDiagnostics() : Flow<BaseResult<PagingModel<MyQueuesForDiagnosticsModel>, String>>

}