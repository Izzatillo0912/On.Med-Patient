package com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForDoctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.addQueueForDoctor.AddQueueForDoctorUseCase
import com.arfomax.onmed.data.network.addQueueForDoctor.AddQueueForDoctorModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQueueForDoctorViewModel @Inject constructor(
    private val addQueueForDoctorUseCase: AddQueueForDoctorUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val addQueueState : StateFlow<PageState> get() = mutablePageState

    fun addQueue(doctorId : Int, queueName : String,
                    queueDate : String, queueType : String, promoCode : String) {

        viewModelScope.launch {
            addQueueForDoctorUseCase.addQueue(
                AddQueueForDoctorModel(queueDate, doctorId, queueName, promoCode, queueType)
            )

                .onStart { mutablePageState.value = PageState.IsLoading("Navbat kiritilmoqda...") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, result.data)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "addQueue")
                    }
                }
        }

    }


}