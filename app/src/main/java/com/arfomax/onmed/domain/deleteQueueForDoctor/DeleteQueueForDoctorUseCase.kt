package com.arfomax.onmed.domain.deleteQueueForDoctor

import javax.inject.Inject

class DeleteQueueForDoctorUseCase @Inject constructor(
    private val deleteQueueForDoctorRepository: DeleteQueueForDoctorRepository
){

    suspend fun deleteQueue(queueId : Int) =
        deleteQueueForDoctorRepository.deleteQueue(queueId)
}