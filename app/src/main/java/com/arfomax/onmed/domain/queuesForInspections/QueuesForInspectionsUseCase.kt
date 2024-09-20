package com.arfomax.onmed.domain.queuesForInspections

import javax.inject.Inject

class QueuesForInspectionsUseCase @Inject constructor(
    private val queuesForInspectionsRepository: QueuesForInspectionsRepository
){

    suspend fun getQueuesForInspections(inspectionId : Int, date : String) =
        queuesForInspectionsRepository.getQueuesForInspection(inspectionId, date)
}