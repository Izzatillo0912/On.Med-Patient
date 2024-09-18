package com.arfomax.onmed.presentation.utils

sealed class PageState {
        data object Init : PageState()
        data class IsLoading(val loadingMessage : String) : PageState()
        data class IsSuccess(val data : Any, val successMessage : String) : PageState()
        data class IsError(val errorMessage : String, val refreshType : String) : PageState()
    }