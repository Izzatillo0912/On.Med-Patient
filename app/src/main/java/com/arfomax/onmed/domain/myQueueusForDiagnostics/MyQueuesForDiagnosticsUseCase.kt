package com.arfomax.onmed.domain.myQueueusForDiagnostics

import javax.inject.Inject

class MyQueuesForDiagnosticsUseCase @Inject constructor(private val repository: MyQueuesForDiagnosticsRepository) {

    suspend fun getMyQueuesForDiagnostics() = repository.myQueuesForDiagnostics()

}