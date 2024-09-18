package com.arfomax.onmed.presentation.ui.activity.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.regionsAndDistricts.RegionsAndDistrictUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionsAndDistrictsViewModel @Inject constructor(
    private val regionsAndDistrictUseCase: RegionsAndDistrictUseCase) : ViewModel() {

    private val regionsAndDistrictsState = MutableStateFlow<PageState>(PageState.Init)
    val state get() = regionsAndDistrictsState

    fun getRegionsAndDistricts() {
        viewModelScope.launch {
            regionsAndDistrictUseCase.getRegionsAndDistricts()
                .onStart {
                    regionsAndDistrictsState.value = PageState.IsLoading("Viloyat va tumanlar qidirilmoqda..")
                }
                .collect { result->
                    when(result) {
                        is BaseResult.Success -> {
                            regionsAndDistrictsState.value = PageState.IsSuccess(result.data, "")
                        }
                        is BaseResult.Error<*> -> {
                            regionsAndDistrictsState.value = PageState.IsError(result.httpError.toString(), "")
                        }
                    }
                }
        }

    }

}