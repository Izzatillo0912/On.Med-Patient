package com.arfomax.onmed.presentation.ui.fragments.numberVerification.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.smsVerification.SmsVerificationUseCase
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsVerificationViewModel @Inject constructor(private val smsVerificationUseCase: SmsVerificationUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val loginState : StateFlow<PageState> get() = mutablePageState

    fun loginRequest(phoneNumber : String, code : Int) {
        viewModelScope.launch {
            smsVerificationUseCase.smsVerification(phoneNumber, code)
                .onStart { mutablePageState.value = PageState.IsLoading("Tasdiqlash kodi tekshirilmoqda..") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, ActionResultMessage.SUCCESS_LOGIN)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "login")
                    }
                    mutablePageState.value = PageState.Init
                }
        }
    }
}