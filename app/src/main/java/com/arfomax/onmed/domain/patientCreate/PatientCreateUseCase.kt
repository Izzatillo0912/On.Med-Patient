package com.arfomax.onmed.domain.patientCreate

import com.arfomax.onmed.data.network.patientCreate.PatientCreateModel
import javax.inject.Inject

class PatientCreateUseCase @Inject constructor(private val patientCreateRepository: PatientCreateRepository) {

    suspend fun patientCreate(patientCreateModel: PatientCreateModel) =
        patientCreateRepository.patientCreate(patientCreateModel)

}