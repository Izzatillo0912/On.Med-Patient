package com.arfomax.onmed.domain.login

import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(phoneNumber : String) : Flow<BaseResult<String, String>>

}