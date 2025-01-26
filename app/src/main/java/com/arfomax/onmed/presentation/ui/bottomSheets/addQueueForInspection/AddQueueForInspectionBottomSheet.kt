package com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForInspection

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.databinding.BottomSheetAddQueueBinding
import com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForDoctor.FromDoctorsAdapter
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.diagnosticQueues.viewModels.QueuesForInspectionViewModel
import com.arfomax.onmed.presentation.ui.fragments.doctors.viewModels.DoctorsViewModel
import com.arfomax.onmed.presentation.ui.utils.EditTextToStateFlow.getDataFromNetWork
import com.arfomax.onmed.presentation.ui.utils.EditTextToStateFlow.getQueryTextChangeStateFlow
import com.arfomax.onmed.presentation.utils.PageState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
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
class AddQueueForInspectionBottomSheet(
    private val queuesForInspectionViewModel: QueuesForInspectionViewModel,
    private val inspectionId : Int, private val date : String) : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetAddQueueBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private lateinit var actionResultDialog2: ActionResultDialog
    private val fromDoctorsAdapter = FromDoctorsAdapter()
    private val doctorsViewModel by viewModels<DoctorsViewModel>()
    private val addQueuesForInspectionViewModel by viewModels<AddQueueForInspectionViewModel>()
    private var queueType = "online"
    private val queueTypeList = arrayListOf("Onlayn","Shifokor tomonidan")
    private var selectedDoctor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
        actionResultDialog2 = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetAddQueueBinding.inflate(inflater, container, false)
        binding.selectDoctor.rvDoctors.adapter = fromDoctorsAdapter
        stateObserve()
        binding.queueType.setItems(queueTypeList)
        return binding.root
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.queueType.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> {
                _,_,_,newItem ->

            when(newItem) {
                "Onlayn" -> {
                    binding.selectDoctor.root.visibility = View.GONE
                    queueType = "online"
                }
                "Shifokor tomonidan" -> {
                    binding.selectDoctor.root.visibility = View.VISIBLE
                    queueType = "from_doctor"
                }
            }
        })

        lifecycleScope.launch {
            binding.selectDoctor.etSearch.getQueryTextChangeStateFlow().debounce(400).filter { doctorName->
                if (doctorName.isEmpty()) { doctorsViewModel.getDoctors(doctorName)
                    return@filter false
                } else return@filter true
            }.distinctUntilChanged().flatMapLatest { query -> getDataFromNetWork(query) }.collect { doctorName->
                doctorsViewModel.getDoctors(doctorName)
            }
        }

        fromDoctorsAdapter.selectedDoctorListener {
            Log.e("LambadaUnit", "selectedListener main")
            selectedDoctor = it
            Log.e("LambadaUnit", "selectedListener selectedDoctor = it")
        }

        binding.btnAddQueue.setOnClickListener { addQueue() }

        actionResultDialog.replayBtnClickListener { addQueue() }

        actionResultDialog2.replayBtnClickListener {
            doctorsViewModel.getDoctors(binding.selectDoctor.etSearch.text.toString())
        }
    }

    private fun addQueue() {

        val name = binding.etName.text.toString()

        if (binding.queueType.text.toString() == "from_doctor" && selectedDoctor == 0) {
            Toast.makeText(requireContext(), "Shifokor belgilanmagan!", Toast.LENGTH_SHORT).show()
        }
        else if (binding.queueType.text.toString() == "from_doctor" && selectedDoctor != 0) {
            addQueuesForInspectionViewModel.addQueue(inspectionId, name, date, selectedDoctor, queueType)
        } else if (binding.queueType.text.toString() != "from_doctor") {
            addQueuesForInspectionViewModel.addQueue(inspectionId, name,
                date, 0, queueType)
        }
    }

    private fun stateObserve() {
        addQueuesForInspectionViewModel.addQueueState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> setStateActionResultDialog(state) }.launchIn(lifecycleScope)

        doctorsViewModel.pageState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setStateActionResultDialog2(state) }.launchIn(lifecycleScope)
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
                val data = state.data as ArrayList<DoctorInfoModel>
                fromDoctorsAdapter.submitList(data, selectedDoctor)
            }
        }
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
                actionResultDialog.showDialog("", ActionResultSetAnimation.SUCCESS, state.successMessage)
                object : CountDownTimer(2500, 2500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        dismiss()
                        queuesForInspectionViewModel.getQueuesForInspections(inspectionId, date)
                    }
                }.start()
            }
        }
    }


}