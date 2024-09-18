package com.arfomax.onmed.presentation.ui.bottomSheets.deleteQueueForDoctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.deleteQueueForDoctor.DeleteQueueForDoctorUseCase
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteQueueForDoctorViewModel @Inject constructor(
    private val deleteQueueForDoctorUseCase: DeleteQueueForDoctorUseCase
) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val deleteQueueState : StateFlow<PageState> get() = mutablePageState

    fun deleteQueue(queueId : Int) {

        viewModelScope.launch {
            deleteQueueForDoctorUseCase.deleteQueue(queueId)

                .onStart { mutablePageState.value = PageState.IsLoading("Navbat o'chirilmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, result.data)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "deleteQueueForDoctor")
                    }
                    mutablePageState.value = PageState.Init
                }
        }

    }


}