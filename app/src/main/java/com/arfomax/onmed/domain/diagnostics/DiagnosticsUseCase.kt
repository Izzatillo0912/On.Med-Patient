package com.arfomax.onmed.domain.diagnostics

import javax.inject.Inject

class DiagnosticsUseCase @Inject constructor(private val diagnosticsRepository: DiagnosticsRepository) {
    suspend fun getAllDiagnostics(search : String) = diagnosticsRepository.getAllDiagnostics(search)

}