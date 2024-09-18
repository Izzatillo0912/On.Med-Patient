package com.arfomax.onmed.presentation.ui.fragments.login.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.login.LoginUseCase
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultMessage
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase : LoginUseCase) : ViewModel() {

    private val mutablePageState = MutableStateFlow<PageState>(PageState.Init)
    val state : StateFlow<PageState> get() = mutablePageState

    fun sendPhoneNumberRequest(phoneNumber : String) {
        viewModelScope.launch {
            loginUseCase.login(phoneNumber)
                .onStart { mutablePageState.value = PageState.IsLoading("Telefon raqamingiz yuborilmoqda") }
                .collect { result->
                    when (result) {
                        is BaseResult.Success -> mutablePageState.value =
                            PageState.IsSuccess(result.data, ActionResultMessage.SUCCESS_SEND_NUMBER)

                        is BaseResult.Error<*> -> mutablePageState.value =
                            PageState.IsError(result.httpError.toString(), "sendPhoneNumber")
                    }
                    mutablePageState.value = PageState.Init
                }
        }
    }

}