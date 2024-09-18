package com.arfomax.onmed.data.network.diagnosticInfo.model


import com.arfomax.onmed.data.common.model.District
import com.arfomax.onmed.data.common.model.Region
import com.google.gson.annotations.SerializedName

data class DiagnosticInfoModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("admin_name")
    val adminName: String,
    @SerializedName("combine_inspections")
    val combineInspections: List<CombineInspection>,
    @SerializedName("diagnostics_name")
    val diagnosticsName: String,
    @SerializedName("diagnostics_rooms")
    val diagnosticsRooms: List<DiagnosticsRoom>,
    @SerializedName("district")
    val district: District,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("region")
    val region: Region
)