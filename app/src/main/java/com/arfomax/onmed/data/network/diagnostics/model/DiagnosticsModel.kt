package com.arfomax.onmed.data.network.diagnostics.model


import com.arfomax.onmed.data.common.model.District
import com.arfomax.onmed.data.common.model.Region
import com.google.gson.annotations.SerializedName

data class DiagnosticsModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("admin_name")
    val adminName: String,
    @SerializedName("diagnostics_name")
    val diagnosticsName: String,
    @SerializedName("district")
    val district: District,
    @SerializedName("id")
    val id: Int,
    @SerializedName("combine_inspections_count")
    val inspectionsCount: Int,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("diagnostics_files")
    val images: List<DiagnosticImageModel>,
    @SerializedName("region")
    val region: Region
)