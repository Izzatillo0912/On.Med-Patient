package com.arfomax.onmed.presentation.ui.fragments.doctorInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.getWorkDays.GetWorkDayUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetWorkDaysViewModel @Inject constructor(private val getWorkDayUseCase: GetWorkDayUseCase) : ViewModel() {

    private val workDaysPageState = MutableStateFlow<PageState>(PageState.Init)
    val getWorDaysState : StateFlow<PageState> get() = workDaysPageState

    fun getWorkDays(doctorId : Int, fromDate : String, toDate : String) {
        viewModelScope.launch {
            getWorkDayUseCase.getWorkDays(doctorId, fromDate, toDate)
                .onStart { workDaysPageState.value = PageState.IsLoading("Shifokor ish kunlari qidirlmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> workDaysPageState.value =
                            PageState.IsSuccess(result.data, "getWorkDays")

                        is BaseResult.Error<*> -> workDaysPageState.value =
                            PageState.IsError(result.httpError.toString(), "getWorkDays")
                    }
                }
        }
    }
}