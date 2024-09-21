package com.arfomax.onmed.presentation.ui.bottomSheets.deleteQueueForInspection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.deleteQueueForInspection.DeleteQueueForInspectionUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteQueueForInspectionViewModel @Inject constructor(
    private val deleteQueueForInspectionUseCase: DeleteQueueForInspectionUseCase
) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val deleteQueueState : StateFlow<PageState> get() = mutablePageState

    fun deleteQueue(queueId : Int) {

        viewModelScope.launch {
            deleteQueueForInspectionUseCase.deleteQueue(queueId)

                .onStart { mutablePageState.value = PageState.IsLoading("Navbat o'chirilmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, result.data)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "deleteQueue")
                    }
                }
        }

    }


}