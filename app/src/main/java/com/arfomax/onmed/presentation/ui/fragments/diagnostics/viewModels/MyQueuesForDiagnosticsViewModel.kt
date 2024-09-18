package com.arfomax.onmed.presentation.ui.fragments.diagnostics.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.myQueueusForDiagnostics.MyQueuesForDiagnosticsUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyQueuesForDiagnosticsViewModel @Inject constructor(
    private val myQueuesForDoctorsUseCase: MyQueuesForDiagnosticsUseCase): ViewModel() {

    private val queuesPageState = MutableStateFlow<PageState>(PageState.Init)
    val myQueues : StateFlow<PageState> get() = queuesPageState

    fun getMyQueuesForDiagnostics() {
        viewModelScope.launch {
            myQueuesForDoctorsUseCase.getMyQueuesForDiagnostics()
                .onStart { queuesPageState.value = PageState.IsLoading("Navbatlar qidirlmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> queuesPageState.value =
                            PageState.IsSuccess(result.data, "getMyQueuesForDiagnostics")

                        is BaseResult.Error<*> -> queuesPageState.value =
                            PageState.IsError(result.httpError.toString(), "getMyQueuesForDiagnostics")
                    }
                }
        }
    }
}