package com.arfomax.onmed.domain.getWorkDays

import com.arfomax.onmed.data.network.getWorkDays.GetWorkDay
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface GetWorkDaysRepository {

    suspend fun getWorkDays(doctorId : Int, fromDate : String, toDate : String) : Flow<BaseResult<ArrayList<GetWorkDay>, String>>

}