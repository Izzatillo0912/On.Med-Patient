package com.arfomax.onmed.domain.deleteQueueForInspection

import javax.inject.Inject

class DeleteQueueForInspectionUseCase @Inject constructor(
    private val deleteQueueForInspectionRepository: DeleteQueueForInspectionRepository
){

    suspend fun deleteQueue(queueId : Int) =
        deleteQueueForInspectionRepository.deleteQueue(queueId)
}