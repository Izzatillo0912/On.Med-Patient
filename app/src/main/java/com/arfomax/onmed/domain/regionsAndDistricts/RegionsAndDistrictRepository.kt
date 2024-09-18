package com.arfomax.onmed.domain.regionsAndDistricts

import com.arfomax.onmed.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import com.arfomax.onmed.domain.regionsAndDistricts.model.RegionsAndDistrictsModel

interface RegionsAndDistrictRepository {

    suspend fun getRegionsAndDistrict() : Flow<BaseResult<ArrayList<RegionsAndDistrictsModel>, String>>

}