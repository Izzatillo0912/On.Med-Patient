package com.arfomax.onmed.domain.queuesForDoctor

import javax.inject.Inject

class QueuesForDoctorUseCase @Inject constructor(
    private val queuesForDoctorRepository: QueuesForDoctorRepository
){

    suspend fun getQueuesForDoctor(date : String, doctorId : Int) =
        queuesForDoctorRepository.getQueuesForDoctor(date, doctorId)
}