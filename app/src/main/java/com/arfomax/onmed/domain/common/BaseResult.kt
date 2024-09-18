package com.arfomax.onmed.domain.common

sealed class BaseResult<out Data : Any, out Error : Any> {
    data class Success<Data : Any>(val data: Data) : BaseResult<Data, Nothing>()
    data class Error<Error : Any>(val httpError: Error) : BaseResult<Nothing, String>()
}