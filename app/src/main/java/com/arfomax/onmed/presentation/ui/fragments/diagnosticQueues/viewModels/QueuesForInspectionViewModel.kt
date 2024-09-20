package com.arfomax.onmed.presentation.ui.fragments.diagnosticQueues.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.queuesForInspections.QueuesForInspectionsUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueuesForInspectionViewModel @Inject constructor(
    private val queuesForInspectionsUseCase: QueuesForInspectionsUseCase
): ViewModel() {

    private val queuesPageState = MutableStateFlow<PageState>(PageState.Init)
    val queuesForInspectionState : StateFlow<PageState> get() = queuesPageState

    fun getQueuesForInspections(inspectionId : Int, date : String) {
        viewModelScope.launch {
            queuesForInspectionsUseCase.getQueuesForInspections(inspectionId, date)
                .onStart { queuesPageState.value = PageState.IsLoading("Navbatlar qidirlmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> queuesPageState.value =
                            PageState.IsSuccess(result.data, "queuesForInspection")

                        is BaseResult.Error<*> -> queuesPageState.value =
                            PageState.IsError(result.httpError.toString(), "queuesForInspection")
                    }
                }
        }
    }
}