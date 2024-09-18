package com.arfomax.onmed.presentation.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.patientCreate.PatientCreateModel
import com.arfomax.onmed.databinding.FragmentLoginBinding
import com.arfomax.onmed.presentation.ui.activity.viewModels.RegionsAndDistrictsViewModel
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.dialogs.selectRegion.SelectRegionDialog
import com.arfomax.onmed.presentation.ui.fragments.login.viewModels.LoginViewModel
import com.arfomax.onmed.presentation.ui.fragments.login.viewModels.PatientCreateViewModel
import com.arfomax.onmed.presentation.ui.utils.KeyboardVisibilityListener
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private val regionsAndDistrictsViewModel by activityViewModels<RegionsAndDistrictsViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val patientCreateViewmodel by viewModels<PatientCreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionResultDialog = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        if (RuntimeCache.userVerified) {
            findNavController().popBackStack()
        }

        stateObserve()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (RuntimeCache.regionsAndDistrictsList.isEmpty()) regionsAndDistrictsViewModel.getRegionsAndDistricts()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        KeyboardVisibilityListener(binding.root) { isVisible ->
            if (isVisible) { binding.logo1.isVisible = false; adjustImageViewConstraints() }
            else { binding.logo1.isVisible = true; resetImageViewConstraints() }
        }

        binding.changeAddress.setOnClickListener {
            SelectRegionDialog(binding.root.context).showDialog(binding.changeAddress)
        }

        binding.btnSignIn.setOnClickListener {
            if (filledInformation()) loginViewModel.sendPhoneNumberRequest(RuntimeCache.userPhoneNumber)
        }

        actionResultDialog.replayBtnClickListener {
            if (it == "sendPhoneNumber") loginViewModel.sendPhoneNumberRequest(RuntimeCache.userPhoneNumber)
            else patientCreate()
        }

    }

    private fun adjustImageViewConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.loginFragment)

        constraintSet.clear(binding.loginCard.id, ConstraintSet.BOTTOM)
        constraintSet.connect(binding.loginCard.id, ConstraintSet.TOP,
            binding.btnBack.id, ConstraintSet.BOTTOM)

        constraintSet.applyTo(binding.loginFragment)

        binding.loginCard.margin(top = 30f)
    }

    private fun resetImageViewConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.loginFragment)

        constraintSet.clear(binding.loginCard.id, ConstraintSet.TOP)
        constraintSet.connect(binding.loginCard.id, ConstraintSet.BOTTOM,
            binding.logo2.id, ConstraintSet.TOP)

        constraintSet.applyTo(binding.loginFragment)
        binding.loginCard.margin(bottom = 30f)
    }

    private fun stateObserve() {

        loginViewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> actionResultDialog(state) }.launchIn(lifecycleScope)

        patientCreateViewmodel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> actionResultDialog(state) }.launchIn(lifecycleScope)
    }

    private fun actionResultDialog(state : PageState) {

        actionResultDialog.dismiss()

        if(state is PageState.IsLoading)
            actionResultDialog.showDialog("", ActionResultSetAnimation.LOADING, state.loadingMessage)
        if (state is PageState.IsSuccess)
            findNavController().navigate(R.id.action_loginFragment_to_numberVerificationFragment)
        if (state is PageState.IsError) {
            if (state.errorMessage == "not_found") patientCreate()
            else actionResultDialog.showDialog(state.refreshType, ActionResultSetAnimation.ERROR, state.errorMessage)
        }

    }

    private fun filledInformation() : Boolean {
        var result = false
        RuntimeCache.userName = binding.patientName.text.toString()
        RuntimeCache.userPhoneNumber = binding.etPhoneNumber.unMasked

        if (RuntimeCache.userName.isEmpty()) binding.patientName.error = "Kiritish lozim!"
        if (RuntimeCache.userPhoneNumber.length != 9) binding.etPhoneNumber.error = "To'liq kiriting!"
        if (RuntimeCache.userName.isNotEmpty() && RuntimeCache.userPhoneNumber.length == 9) result = true
        return result
    }

    private fun patientCreate() {
        patientCreateViewmodel.createPatientRequest(PatientCreateModel("Farg'ona",
            RuntimeCache.userDistrictId, RuntimeCache.userName,
            RuntimeCache.userPhoneNumber, RuntimeCache.userRegionId))
    }
}