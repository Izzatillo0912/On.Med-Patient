package com.arfomax.onmed.presentation.ui.fragments.doctors.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.doctors.DoctorsUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(private val doctorsUseCase: DoctorsUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val pageState get() = mutablePageState

    fun getDoctors(search : String) {
        viewModelScope.launch {
            doctorsUseCase.doctors(search)
                .onStart {
                    mutablePageState.value = PageState.IsLoading("Shifokorlar qidirlmoqda.")
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