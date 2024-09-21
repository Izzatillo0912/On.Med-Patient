package com.arfomax.onmed.presentation.ui.fragments.diagnostics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.R
import com.arfomax.onmed.data.common.model.PagingModel
import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticsModel
import com.arfomax.onmed.data.network.myQueuesForDiagnostics.MyQueuesForDiagnosticsModel
import com.arfomax.onmed.databinding.FragmentDiagnosticsBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.diagnostics.adapter.DiagnosticsAdapter
import com.arfomax.onmed.presentation.ui.fragments.diagnostics.adapter.MyQueuesForDiagnosticsAdapter
import com.arfomax.onmed.presentation.ui.fragments.diagnostics.viewModels.DiagnosticsViewModel
import com.arfomax.onmed.presentation.ui.fragments.diagnostics.viewModels.MyQueuesForDiagnosticsViewModel
import com.arfomax.onmed.presentation.ui.utils.EditTextToStateFlow.getDataFromNetWork
import com.arfomax.onmed.presentation.ui.utils.EditTextToStateFlow.getQueryTextChangeStateFlow
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiagnosticsFragment : Fragment() {

    private lateinit var binding : FragmentDiagnosticsBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private lateinit var actionResultDialog2: ActionResultDialog
    private val diagnosticsAdapter = DiagnosticsAdapter()
    private val myQueuesForDiagnosticsAdapter = MyQueuesForDiagnosticsAdapter()
    private val diagnosticsViewModel by viewModels<DiagnosticsViewModel>()
    private val myQueueForDiagnosticsViewModel by viewModels<MyQueuesForDiagnosticsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
        actionResultDialog2 = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiagnosticsBinding.inflate(inflater, container, false)
        binding.rvDiagnostics.adapter = diagnosticsAdapter
        binding.rvQueuesForDiagnostics.adapter = myQueuesForDiagnosticsAdapter
        stateObserver()
        return binding.root
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Hawk.get<Boolean>(Constants.USER_VERIFIED) == true){
            myQueueForDiagnosticsViewModel.getMyQueuesForDiagnostics()
            binding.tvMyQueueForDiagnostics.visibility = View.VISIBLE
            binding.rvQueuesForDiagnostics.visibility = View.VISIBLE
            binding.tvAllDiagnostics.margin(top = 0f)
        }

        else {
            binding.tvMyQueueForDiagnostics.visibility = View.GONE
            binding.rvQueuesForDiagnostics.visibility = View.GONE
            binding.tvAllDiagnostics.margin(top = 15f)
        }

        binding.btnProfile.setOnClickListener {
            if (Hawk.get<Boolean>(Constants.USER_VERIFIED) == true)
                findNavController().navigate(R.id.action_diagnosticsFragment_to_profileFragment)
            else findNavController().navigate(R.id.action_diagnosticsFragment_to_loginFragment)
        }

        binding.btnGoDoctors.setOnClickListener { findNavController().popBackStack() }

        lifecycleScope.launch {
            binding.etSearch.getQueryTextChangeStateFlow().debounce(400).filter { doctorName->
                if (doctorName.isEmpty()) { diagnosticsViewModel.getAllDiagnostics(doctorName)
                    return@filter false
                } else return@filter true
            }.distinctUntilChanged().flatMapLatest { query -> getDataFromNetWork(query) }.collect { doctorName->
                diagnosticsViewModel.getAllDiagnostics(doctorName)
            }
        }

        myQueuesForDiagnosticsAdapter.queueForDoctorClickListener {
            RuntimeCache.combineInspection = it.diagnosticsInspection
            RuntimeCache.myQueueDate = it.date.dropLast(9)
            findNavController().navigate(R.id.action_diagnosticsFragment_to_diagnosticQueuesFragment)
        }

        diagnosticsAdapter.diagnosticClickListener {
            RuntimeCache.diagnosticsModel = it
            findNavController().navigate(R.id.action_diagnosticsFragment_to_diagnosticInfoFragment)
        }

    }

    private fun stateObserver() {

        diagnosticsViewModel.pageState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setStateActionResultDialog(state) }.launchIn(lifecycleScope)

        myQueueForDiagnosticsViewModel.myQueues.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setStateActionResultDialog2(state) }.launchIn(lifecycleScope)
    }

    private fun setStateActionResultDialog(state : PageState) {
        when(state) {

            is PageState.Init -> Unit

            is PageState.IsLoading -> {
                actionResultDialog.showDialog("", ActionResultSetAnimation.LOADING, state.loadingMessage)
            }

            is PageState.IsError -> {
                actionResultDialog.showDialog(state.refreshType, ActionResultSetAnimation.ERROR, state.errorMessage)
            }

            is PageState.IsSuccess -> {
                actionResultDialog.dismiss()
                val data = state.data as ArrayList<DiagnosticsModel>
                diagnosticsAdapter.submitList(data)
            }
        }
    }

    private fun setStateActionResultDialog2(state : PageState) {
        when(state) {

            is PageState.Init -> Unit

            is PageState.IsLoading -> {
                actionResultDialog2.showDialog("", ActionResultSetAnimation.LOADING, state.loadingMessage)
            }

            is PageState.IsError -> {
                actionResultDialog2.showDialog(state.refreshType, ActionResultSetAnimation.ERROR, state.errorMessage)
            }

            is PageState.IsSuccess -> {
                actionResultDialog2.dismiss()
                val data = state.data as PagingModel<MyQueuesForDiagnosticsModel>
                myQueuesForDiagnosticsAdapter.submitList(data.results)
                if (data.results.isEmpty()) {
                    binding.tvAllDiagnostics.margin(top = 10f)
                    binding.tvMyQueueForDiagnostics.visibility = View.GONE
                }
                else {
                    binding.tvAllDiagnostics.margin(top = 0f)
                    binding.tvMyQueueForDiagnostics.visibility = View.VISIBLE
                }
            }
        }
    }
}