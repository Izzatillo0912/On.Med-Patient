package com.arfomax.onmed.domain.myQueueusForDoctors

import javax.inject.Inject

class MyQueuesForDoctorsUseCase @Inject constructor(private val repository: MyQueuesForDoctorsRepository) {

    suspend fun getMyQueuesForDoctors() = repository.myQueuesForDoctors()

}