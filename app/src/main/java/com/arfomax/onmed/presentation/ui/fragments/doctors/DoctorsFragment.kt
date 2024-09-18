package com.arfomax.onmed.presentation.ui.fragments.doctors

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
import com.arfomax.onmed.data.common.model.District
import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.databinding.FragmentDoctorsBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.doctors.adapters.DoctorsAdapter
import com.arfomax.onmed.presentation.ui.fragments.doctors.adapters.QueuesMeForDoctorsAdapter
import com.arfomax.onmed.presentation.ui.fragments.doctors.viewModels.DoctorsViewModel
import com.arfomax.onmed.presentation.ui.fragments.doctors.viewModels.MyQueuesForDoctorsViewModel
import com.arfomax.onmed.presentation.ui.utils.EditTextToStateFlow.getDataFromNetWork
import com.arfomax.onmed.presentation.ui.utils.EditTextToStateFlow.getQueryTextChangeStateFlow
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
class DoctorsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorsBinding
    private lateinit var actionResultDialog : ActionResultDialog
    private lateinit var actionResultDialog2 : ActionResultDialog
    private val doctorsViewModel by viewModels<DoctorsViewModel>()
    private val myQueuesForDoctorsViewModel by viewModels<MyQueuesForDoctorsViewModel>()
    private val queuesMeForDoctorsAdapter = QueuesMeForDoctorsAdapter()
    private val doctorsAdapter = DoctorsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
        actionResultDialog2 = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoctorsBinding.inflate(inflater, container, false)
        binding.rvQueuesForDoctor.adapter = queuesMeForDoctorsAdapter
        binding.rvDoctors.adapter = doctorsAdapter
        stateObserver()
        return binding.root
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myQueuesForDoctorsViewModel.getMyQueuesForDoctors()

        binding.btnProfile.setOnClickListener {
            if (Hawk.get<Boolean>(Constants.USER_VERIFIED) == true)
                findNavController().navigate(R.id.action_doctorsFragment_to_profileFragment)
            else findNavController().navigate(R.id.action_doctorsFragment_to_loginFragment)
        }

        binding.btnGoClinics.setOnClickListener {
            findNavController().navigate(R.id.action_doctorsFragment_to_diagnosticsFragment)
        }

        lifecycleScope.launch {
            binding.etSearch.getQueryTextChangeStateFlow().debounce(400).filter { doctorName->
                if (doctorName.isEmpty()) { doctorsViewModel.getDoctors(doctorName)
                    return@filter false
                } else return@filter true
            }.distinctUntilChanged().flatMapLatest { query -> getDataFromNetWork(query) }.collect { doctorName->
                doctorsViewModel.getDoctors(doctorName)
            }
        }

        queuesMeForDoctorsAdapter.queueForDoctorClickListener {
            RuntimeCache.doctorWorkDays.clear()
            RuntimeCache.doctorInfoModel = it.doctor
            RuntimeCache.myQueueDate = it.date.dropLast(9)
            findNavController().navigate(R.id.action_doctorsFragment_to_doctorQueuesFragment)
        }

        doctorsAdapter.doctorClickListener {
            RuntimeCache.doctorInfoModel = it
            findNavController().navigate(R.id.action_doctorsFragment_to_doctorInfoFragment)
        }

        actionResultDialog.replayBtnClickListener {
            doctorsViewModel.getDoctors(binding.etSearch.text.toString())
        }
    }

    private fun stateObserver() {

        doctorsViewModel.pageState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setStateActionResultDialog(state) }.launchIn(lifecycleScope)

        myQueuesForDoctorsViewModel.myQueues.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
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
                val data = state.data as ArrayList<DoctorInfoModel>
                doctorsAdapter.submitList(data)
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
                val data = state.data as QueuesModel
                queuesMeForDoctorsAdapter.submitList(data.results)
            }
        }
    }
}