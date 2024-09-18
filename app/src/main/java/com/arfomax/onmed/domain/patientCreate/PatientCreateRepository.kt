package com.arfomax.onmed.domain.patientCreate

import com.arfomax.onmed.data.network.patientCreate.PatientCreateModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface PatientCreateRepository {

    suspend fun patientCreate(patientCreateModel: PatientCreateModel) : Flow<BaseResult<String, String>>

}