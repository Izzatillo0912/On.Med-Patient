package com.arfomax.onmed.domain.smsVerification

import javax.inject.Inject

class SmsVerificationUseCase @Inject constructor(
    private val smsVerificationRepository: SmsVerificationRepository) {

    suspend fun smsVerification(phoneNumber: String, code : Int) =
        smsVerificationRepository.smsVerification(phoneNumber, code)

}