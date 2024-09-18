package com.arfomax.onmed.domain.addQueueForDoctor

import com.arfomax.onmed.data.network.addQueueForDoctor.AddQueueForDoctorModel
import javax.inject.Inject

class AddQueueForDoctorUseCase @Inject constructor(
    private val addQueueForDoctorRepository: AddQueueForDoctorRepository
){

    suspend fun addQueue(addQueueForDoctorModel: AddQueueForDoctorModel) =
        addQueueForDoctorRepository.addQueue(addQueueForDoctorModel)
}