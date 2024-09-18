package com.arfomax.onmed.presentation.utils

import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.domain.regionsAndDistricts.model.RegionsAndDistrictsModel

object RuntimeCache {

    var regionsAndDistrictsList = arrayListOf<RegionsAndDistrictsModel>()
    var userId : Int = 0
    var userName : String = ""
    var userVerified : Boolean = false
    var userPhoneNumber : String = ""
    var userRegionName : String = ""
    var userDistrictName : String = ""
    var userRegionId : Int = 1
    var userDistrictId : Int = 1


    var doctorInfoModel : DoctorInfoModel? = null
    var doctorWorkDays = ArrayList<String>()
    var myQueueDate = ""
}