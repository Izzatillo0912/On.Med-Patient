package com.arfomax.onmed.presentation.ui.fragments.login.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.data.network.patientCreate.PatientCreateModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.login.LoginUseCase
import com.arfomax.onmed.domain.patientCreate.PatientCreateUseCase
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientCreateViewModel @Inject constructor(private val patientCreateUseCase: PatientCreateUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val state : StateFlow<PageState> get() = mutablePageState

    fun createPatientRequest(patientCreateModel: PatientCreateModel) {
        viewModelScope.launch {
            patientCreateUseCase.patientCreate(patientCreateModel)
                .onStart { mutablePageState.value = PageState.IsLoading("Ro'yxatdan o'tqazilmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, result.data)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "patientCreate")
                    }
                }
        }
    }

}