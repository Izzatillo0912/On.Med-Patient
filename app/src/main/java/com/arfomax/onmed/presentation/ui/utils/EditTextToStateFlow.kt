package com.arfomax.onmed.presentation.ui.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

object EditTextToStateFlow {

    fun EditText.getQueryTextChangeStateFlow() : StateFlow<String> {
        val query = MutableStateFlow("")
        addTextChangedListener { query.value = text.toString() }
        return query
    }

    fun getDataFromNetWork(query : String) : Flow<String> {
        return flow {
            delay(400)
            emit(query)
        }
    }

}