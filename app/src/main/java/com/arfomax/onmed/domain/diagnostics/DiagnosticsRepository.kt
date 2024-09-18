package com.arfomax.onmed.domain.diagnostics

import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticsModel
import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface DiagnosticsRepository {

    suspend fun getAllDiagnostics(search : String) : Flow<BaseResult<ArrayList<DiagnosticsModel>, String>>

}