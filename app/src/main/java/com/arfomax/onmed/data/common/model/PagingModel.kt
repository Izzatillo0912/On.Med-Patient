package com.arfomax.onmed.data.common.model

data class PagingModel<T>(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: ArrayList<T>

)
