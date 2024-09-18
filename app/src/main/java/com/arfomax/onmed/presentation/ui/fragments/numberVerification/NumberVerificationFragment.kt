package com.arfomax.onmed.presentation.ui.fragments.numberVerification

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.smsVerification.model.SmsVerificationResponseModel
import com.arfomax.onmed.databinding.FragmentNumberVerificationBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.numberVerification.viewModels.SelectInputViewModel
import com.arfomax.onmed.presentation.ui.fragments.numberVerification.viewModels.SmsVerificationViewModel
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NumberVerificationFragment : Fragment() {

    private lateinit var binding: FragmentNumberVerificationBinding
    private lateinit var selectInputViewModel: SelectInputViewModel
    private lateinit var actionResultDialog: ActionResultDialog
    private val smsVerificationViewModel by viewModels<SmsVerificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionResultDialog = ActionResultDialog(this)
        selectInputViewModel = ViewModelProvider(this)[SelectInputViewModel :: class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNumberVerificationBinding.inflate(inflater, container, false)
        selectedInputListener(); writeCodeListener(); deleteCodeListener(); fullCodeListener(); sendCodeActionResult()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.code1.setOnClickListener {selectInputViewModel.selectedInput(1)}
        binding.code2.setOnClickListener {selectInputViewModel.selectedInput(2)}
        binding.code3.setOnClickListener {selectInputViewModel.selectedInput(3)}
        binding.code4.setOnClickListener {selectInputViewModel.selectedInput(4)}
        binding.code5.setOnClickListener {selectInputViewModel.selectedInput(5)}
        binding.code6.setOnClickListener {selectInputViewModel.selectedInput(6)}

        binding.num0.setOnClickListener { selectInputViewModel.writeCode(0) }
        binding.num1.setOnClickListener { selectInputViewModel.writeCode(1) }
        binding.num2.setOnClickListener { selectInputViewModel.writeCode(2) }
        binding.num3.setOnClickListener { selectInputViewModel.writeCode(3) }
        binding.num4.setOnClickListener { selectInputViewModel.writeCode(4) }
        binding.num5.setOnClickListener { selectInputViewModel.writeCode(5) }
        binding.num6.setOnClickListener { selectInputViewModel.writeCode(6) }
        binding.num7.setOnClickListener { selectInputViewModel.writeCode(7) }
        binding.num8.setOnClickListener { selectInputViewModel.writeCode(8) }
        binding.num9.setOnClickListener { selectInputViewModel.writeCode(9) }
        binding.numDel.setOnClickListener { selectInputViewModel.deleteInput() }
    }

    private fun selectedInputListener() {
        selectInputViewModel.selectedInputLiveData.observe(viewLifecycleOwner) { list ->
            when(list[0]) {
                1 -> binding.code1.setBackgroundResource(R.drawable.sms_code_not_selected_background)
                2 -> binding.code2.setBackgroundResource(R.drawable.sms_code_not_selected_background)
                3 -> binding.code3.setBackgroundResource(R.drawable.sms_code_not_selected_background)
                4 -> binding.code4.setBackgroundResource(R.drawable.sms_code_not_selected_background)
                5 -> binding.code5.setBackgroundResource(R.drawable.sms_code_not_selected_background)
                6 -> binding.code6.setBackgroundResource(R.drawable.sms_code_not_selected_background)
            }
            when(list[1]) {
                1 -> binding.code1.setBackgroundResource(R.drawable.sms_code_selected_background)
                2 -> binding.code2.setBackgroundResource(R.drawable.sms_code_selected_background)
                3 -> binding.code3.setBackgroundResource(R.drawable.sms_code_selected_background)
                4 -> binding.code4.setBackgroundResource(R.drawable.sms_code_selected_background)
                5 -> binding.code5.setBackgroundResource(R.drawable.sms_code_selected_background)
                6 -> binding.code6.setBackgroundResource(R.drawable.sms_code_selected_background)
            }
        }
    }

    private fun writeCodeListener() {
        selectInputViewModel.writeCodeLiveData.observe(viewLifecycleOwner) { list->
            val selectedInput : TextView? = when(list[0]) {
                1-> binding.code1; 2 -> binding.code2; 3 -> binding.code3
                4 -> binding.code4; 5 -> binding.code5; 6 -> binding.code6; else -> null
            }
            if (selectedInput != null && list[1] != 11) {
                selectedInput.text = list[1].toString()
                selectInputViewModel.addCode(list[0], list[1].toString())
                selectInputViewModel.nextInput()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun deleteCodeListener() {
        selectInputViewModel.deleteCodeLiveData.observe(viewLifecycleOwner) {
            val input : TextView? = when(it) {
                1-> binding.code1; 2 -> binding.code2; 3 -> binding.code3
                4 -> binding.code4; 5 -> binding.code5; 6 -> binding.code6; else -> null
            }
            if (input != null) {
                if (input.text.isEmpty()) selectInputViewModel.beforeInput()
                else {
                    input.text = ""
                    selectInputViewModel.addCode(it, "")
                }
            }
        }
    }

    private fun fullCodeListener() {
        selectInputViewModel.fullInputLiveData.observe(viewLifecycleOwner) { codeList->
            var fullInput = true
            codeList.forEach { if (it.isEmpty()) fullInput = false }

            if (fullInput) smsVerificationViewModel.loginRequest(
                RuntimeCache.userPhoneNumber, codeList.joinToString("").toInt())
        }
    }

    private fun sendCodeActionResult() {
        smsVerificationViewModel.loginState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state->
                when(state) {
                    is PageState.Init -> {}
                    is PageState.IsLoading -> actionResultDialog.showDialog("", ActionResultSetAnimation.LOADING, state.loadingMessage)
                    is PageState.IsError -> actionResultDialog.showDialog("", ActionResultSetAnimation.ERROR, state.errorMessage)
                    is PageState.IsSuccess -> {
                        val data = state.data as SmsVerificationResponseModel
                        RuntimeCache.userVerified = true
                        RuntimeCache.userId = data.patient.id
                        RuntimeCache.userName = data.patient.firstName
                        Hawk.put(Constants.TOKEN, data.accessToken)
                        Hawk.put(Constants.USER_ID, data.patient.id)
                        Hawk.put(Constants.USER_VERIFIED, true)
                        Hawk.put(Constants.USER_NAME, data.patient.firstName)
                        Hawk.put(Constants.PHONE_NUMBER, data.patient.phone)
                        Hawk.put(Constants.REGION_NAME, data.patient.region.nameUz + " -> " + data.patient.district.nameUz)
                        Hawk.put(Constants.REGION_ID, data.patient.region.id)
                        Hawk.put(Constants.DISTRICT_ID, data.patient.district.id)
                        goMainPage()
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun goMainPage() {
        actionResultDialog.dismiss()
        val codeList = listOf(binding.code1, binding.code2, binding.code3, binding.code4, binding.code5, binding.code6)
        lifecycleScope.launch {
            codeList.forEach { view ->
                view.setBackgroundResource(R.drawable.sms_code_full_background)
                if (view == binding.code6) { delay(500)
                    findNavController().popBackStack()
                } else delay(80L)
            }
        }
        findNavController().popBackStack()
    }
}