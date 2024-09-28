package com.arfomax.onmed.presentation.ui.fragments.profile

import android.graphics.pdf.PdfDocument.Page
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfomax.onmed.data.network.updateMe.UpdateMeModel
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.domain.updateMe.UpdateMeUseCase
import com.arfomax.onmed.presentation.utils.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMeViewModel @Inject constructor(private val updateMeUseCase: UpdateMeUseCase) : ViewModel() {

    private val updateMeMutableState = MutableStateFlow<PageState>(PageState.Init)
    val updatePageState get() = updateMeMutableState

    fun updateMe(selectedRegionId : Int, selectedDistrictId : Int, name : String) {
        viewModelScope.launch {

            updateMeUseCase.invoke(UpdateMeModel("Belgilanmagan", selectedDistrictId, name, selectedRegionId))
                .onStart {
                    updateMeMutableState.emit(PageState.IsLoading("O'zgartirilmoqda.."))
                }.collect { result ->

                    if (result is BaseResult.Success) updateMeMutableState.emit(PageState.IsSuccess(result.data, result.data))
                    else updateMeMutableState.emit(PageState.IsError(result.toString(), "updateMe"))

                    updateMeMutableState.emit(PageState.Init)
                }

        }
    }

}