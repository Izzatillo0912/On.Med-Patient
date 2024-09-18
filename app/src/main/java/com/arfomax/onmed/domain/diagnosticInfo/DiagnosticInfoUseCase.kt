package com.arfomax.onmed.domain.diagnosticInfo

import javax.inject.Inject

class DiagnosticInfoUseCase @Inject constructor(private val diagnosticInfoRepository: DiagnosticInfoRepository) {

    suspend fun getDiagnosticInfo(diagnosticId : Int) = diagnosticInfoRepository.getDiagnosticInfo(diagnosticId)

}