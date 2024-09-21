package com.arfomax.onmed.domain.addQueueForInspection

import com.arfomax.onmed.data.network.addQueueForInspection.AddQueueForInspectionModel
import javax.inject.Inject

class AddQueueForInspectionUseCase @Inject constructor(
    private val addQueueForInspectionRepository: AddQueueForInspectionRepository
){

    suspend fun addQueue(addQueueForInspectionModel: AddQueueForInspectionModel) =
        addQueueForInspectionRepository.addQueue(addQueueForInspectionModel)
}