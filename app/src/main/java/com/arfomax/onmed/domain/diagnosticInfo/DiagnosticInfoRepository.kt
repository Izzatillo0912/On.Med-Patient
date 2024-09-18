package com.arfomax.onmed.domain.diagnosticInfo

import com.arfomax.onmed.data.network.diagnosticInfo.model.DiagnosticInfoModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface DiagnosticInfoRepository {

    suspend fun getDiagnosticInfo(diagnosticId : Int) : Flow<BaseResult<DiagnosticInfoModel, String>>

}