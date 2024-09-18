package com.arfomax.onmed.presentation.ui.fragments.doctorQueues.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.updateQueueDateForDoctor.UpdateQueueDateForDoctorUseCase
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateDateForMeViewModel @Inject constructor(
    private val updateQueueDateForDoctorUseCase: UpdateQueueDateForDoctorUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val updateQueueDate : StateFlow<PageState> get() = mutablePageState

    fun updateQueueDate(queueId : Int, date : String) {

        viewModelScope.launch {
            updateQueueDateForDoctorUseCase.updateQueueDate(queueId, date)

                .onStart { mutablePageState.value = PageState.IsLoading("Boshqa kunga ko'chirilmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, result.data)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "updateQueueDate")
                    }
                    mutablePageState.value = PageState.Init
                }
        }

    }

}