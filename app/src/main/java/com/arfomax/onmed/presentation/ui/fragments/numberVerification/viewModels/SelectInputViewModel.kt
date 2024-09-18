package com.arfomax.onmed.presentation.ui.fragments.numberVerification.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectInputViewModel : ViewModel() {

    private val selectInputMutableLiveData = MutableLiveData(arrayListOf(1,1))
    val selectedInputLiveData get() = selectInputMutableLiveData

    private val writeCodeMutableLiveData = MutableLiveData(arrayListOf(1,11))
    val writeCodeLiveData get() = writeCodeMutableLiveData

    private val deleteCodeMutableLiveData = MutableLiveData<Int>()
    val deleteCodeLiveData get() = deleteCodeMutableLiveData

    private val fullInputMutableLiveData = MutableLiveData(arrayListOf("","","","","",""))
    val fullInputLiveData get() = fullInputMutableLiveData

    fun selectedInput(input: Int) = selectInputMutableLiveData.postValue(arrayListOf(selectedInputLiveData.value!![1], input))

    fun nextInput() = selectedInput(selectedInputLiveData.value!![1] + 1)

    fun deleteInput() = deleteCodeMutableLiveData.postValue(selectedInputLiveData.value!![1])

    fun beforeInput() = selectedInput(
        if (selectedInputLiveData.value!![1] != 1) selectedInputLiveData.value!![1]-1 else 1
    )

    fun writeCode(code : Int) = writeCodeMutableLiveData.postValue(arrayListOf(selectInputMutableLiveData.value!![1], code))

    fun addCode(position : Int, code : String) {
        val fullCode = fullInputLiveData.value
        fullCode!![position-1] = code
        fullInputMutableLiveData.postValue(fullCode)
    }
}