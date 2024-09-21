package com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForInspection

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.databinding.BottomSheetAddQueueBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.diagnosticQueues.viewModels.QueuesForInspectionViewModel
import com.arfomax.onmed.presentation.utils.PageState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddQueueForInspectionBottomSheet(
    private val queuesForInspectionViewModel: QueuesForInspectionViewModel,
    private val inspectionId : Int, private val date : String) : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetAddQueueBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private val addQueuesForInspectionViewModel by viewModels<AddQueueForInspectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetAddQueueBinding.inflate(inflater, container, false)
        stateObserve()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddQueue.setOnClickListener { addQueue() }

        actionResultDialog.replayBtnClickListener { addQueue() }
    }

    private fun addQueue() {
        val name = binding.etName.text.toString()
        addQueuesForInspectionViewModel.addQueue(inspectionId, name, date,
            binding.etPromoCode.text.toString().trim(),"from_doctor")
    }

    private fun stateObserve() {
        addQueuesForInspectionViewModel.addQueueState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> setStateActionResultDialog(state) }.launchIn(lifecycleScope)
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