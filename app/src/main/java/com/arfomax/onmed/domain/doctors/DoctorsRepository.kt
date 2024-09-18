package com.arfomax.onmed.domain.doctors

import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface DoctorsRepository {

    suspend fun doctors(search : String) : Flow<BaseResult<ArrayList<DoctorInfoModel>, String>>

}