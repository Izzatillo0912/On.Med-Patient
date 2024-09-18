package com.arfomax.onmed.domain.updateQueueDateForDoctor

import javax.inject.Inject

class UpdateQueueDateForDoctorUseCase @Inject constructor(
    private val updateQueueDateForDoctorRepository: UpdateQueueDateForDoctorRepository
) {

    suspend fun updateQueueDate(queueId : Int, date : String) =
        updateQueueDateForDoctorRepository.updateQueueDate(queueId, date)
}