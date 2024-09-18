package com.arfomax.onmed.domain.regionsAndDistricts

import javax.inject.Inject

class RegionsAndDistrictUseCase @Inject constructor(private val regionsAndDistrictRepository: RegionsAndDistrictRepository){

    suspend fun getRegionsAndDistricts() = regionsAndDistrictRepository.getRegionsAndDistrict()

}