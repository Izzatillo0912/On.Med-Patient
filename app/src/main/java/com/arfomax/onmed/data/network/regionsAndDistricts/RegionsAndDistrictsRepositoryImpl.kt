package com.arfomax.onmed.data.network.regionsAndDistricts

import android.util.Log
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.regionsAndDistricts.RegionsAndDistrictRepository
import com.arfomax.onmed.domain.regionsAndDistricts.model.DistrictModel
import com.arfomax.onmed.domain.regionsAndDistricts.model.RegionsAndDistrictsModel
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegionsAndDistrictsRepositoryImpl @Inject constructor(
    private val regionsAndDistrictsService: RegionsAndDistrictsService
) :
    RegionsAndDistrictRepository {

    override suspend fun getRegionsAndDistrict(): Flow<BaseResult<ArrayList<RegionsAndDistrictsModel>, String>> {
        return flow {
            try {
                val response = regionsAndDistrictsService.getRegionsAndDistricts()
                if (response.isSuccessful) {
                    response.body()?.let { data->
                        val regionsList = arrayListOf<RegionsAndDistrictsModel>()
                        for (i in data.withIndex()) regionsList.add(
                            RegionsAndDistrictsModel(i.value.nameUz, i.value.id, arrayListOf(
                            DistrictModel(i.value.regionDistricts[i.index].nameUz, i.value.regionDistricts[i.index].id)
                        ))
                        )
                        emit(BaseResult.Success(regionsList))
                    }
                }
                else {
                    Log.e("RegionsAndDistrictsRepositoryImpl", "getRegionsAndDistrict errorMessage : " +
                            "${response.errorBody()?.string()} // code :${response.code()}")
                    emit(BaseResult.Error(ActionResultMessage.ERROR))
                }
            }
            catch (t : Throwable) {
                Log.e("RegionsAndDistrictsRepositoryImpl", "getRegionsAndDistrict throwable : ${t.localizedMessage}")
                emit(BaseResult.Error(ActionResultMessage.TIME_OUT))
            }
        }
    }
}