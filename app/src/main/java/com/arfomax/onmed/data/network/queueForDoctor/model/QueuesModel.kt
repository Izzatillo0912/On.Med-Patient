package com.arfomax.onmed.data.network.queueForDoctor.model

import com.google.gson.annotations.SerializedName

data class QueuesModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: ArrayList<QueueModel>
)