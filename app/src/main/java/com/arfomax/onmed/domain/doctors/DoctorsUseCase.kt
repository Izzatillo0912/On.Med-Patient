package com.arfomax.onmed.domain.doctors

import javax.inject.Inject

class DoctorsUseCase @Inject constructor(private val doctorsRepository: DoctorsRepository) {

    suspend fun doctors(search : String) = doctorsRepository.doctors(search)

}