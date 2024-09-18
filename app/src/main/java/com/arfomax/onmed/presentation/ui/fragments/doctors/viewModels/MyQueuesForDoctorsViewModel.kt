package com.arfomax.onmed.presentation.ui.fragments.doctors.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.myQueueusForDoctors.MyQueuesForDoctorsUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyQueuesForDoctorsViewModel @Inject constructor(
    private val myQueuesForDoctorsUseCase: MyQueuesForDoctorsUseCase
): ViewModel() {

    private val queuesPageState = MutableStateFlow<PageState>(PageState.Init)
    val myQueues : StateFlow<PageState> get() = queuesPageState

    fun getMyQueuesForDoctors() {
        viewModelScope.launch {
            myQueuesForDoctorsUseCase.getMyQueuesForDoctors()
                .onStart { queuesPageState.value = PageState.IsLoading("Navbatlar qidirlmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> queuesPageState.value =
                            PageState.IsSuccess(result.data, "getMyQueuesForDoctors")

                        is BaseResult.Error<*> -> queuesPageState.value =
                            PageState.IsError(result.httpError.toString(), "getMyQueuesForDoctors")
                    }
                }
        }
    }
}