package com.arfomax.onmed.domain.getWorkDays

import javax.inject.Inject

class GetWorkDayUseCase @Inject constructor(private val getWorkDaysRepository: GetWorkDaysRepository) {

    suspend fun getWorkDays(doctorId : Int, fromDate : String, toDate : String) =
        getWorkDaysRepository.getWorkDays(doctorId, fromDate, toDate)

}