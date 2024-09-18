package com.arfomax.onmed.domain.login

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend fun login(phoneNumber : String) = loginRepository.login(phoneNumber)
}