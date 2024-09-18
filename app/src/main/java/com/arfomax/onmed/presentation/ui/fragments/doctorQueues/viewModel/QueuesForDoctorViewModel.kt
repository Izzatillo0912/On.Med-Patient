package com.arfomax.onmed.presentation.ui.fragments.doctorQueues.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.queuesForDoctor.QueuesForDoctorUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueuesForDoctorViewModel @Inject constructor(private val queuesForDoctorUseCase: QueuesForDoctorUseCase): ViewModel() {

    private val queuesPageState = MutableStateFlow<PageState>(PageState.Init)
    val queuesForMeState : StateFlow<PageState> get() = queuesPageState

    fun getQueuesForDoctor(doctorId : Int, date : String) {
        viewModelScope.launch {
            queuesForDoctorUseCase.getQueuesForDoctor(date, doctorId)
                .onStart { queuesPageState.value = PageState.IsLoading("Navbatlar qidirlmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> queuesPageState.value =
                            PageState.IsSuccess(result.data, "getQueuesForDoctor")

                        is BaseResult.Error<*> -> queuesPageState.value =
                            PageState.IsError(result.httpError.toString(), "getQueuesForDoctor")
                    }
                }
        }
    }
}