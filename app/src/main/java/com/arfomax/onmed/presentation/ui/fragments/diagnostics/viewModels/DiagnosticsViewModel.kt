package com.arfomax.onmed.presentation.ui.fragments.diagnostics.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.diagnostics.DiagnosticsUseCase
import com.arfomax.onmed.domain.doctors.DoctorsUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnosticsViewModel @Inject constructor(private val diagnosticsUseCase: DiagnosticsUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val pageState get() = mutablePageState

    fun getAllDiagnostics(search : String) {
        viewModelScope.launch {
            diagnosticsUseCase.getAllDiagnostics(search)
                .onStart {
                    mutablePageState.value = PageState.IsLoading("Diagnostikalar qidirlmoqda..")
                }
                .collect { result->
                    when(result) {
                        is BaseResult.Success -> {
                            mutablePageState.value = PageState.IsSuccess(result.data, "")
                        }
                        is BaseResult.Error<*> -> {
                            mutablePageState.value = PageState.IsError(result.httpError.toString(), "")
                        }
                    }
                }
        }

    }

}