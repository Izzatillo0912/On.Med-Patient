package com.arfomax.onmed.presentation.ui.bottomSheets.deleteQueueForDoctor

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
import com.arfomax.onmed.databinding.BottomSheetDeleteQueueBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.doctorQueues.viewModel.QueuesForDoctorViewModel
import com.arfomax.onmed.presentation.utils.PageState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DeleteQueueBottomSheet(val queueId : Int, val queueForDoctorViewModel: QueuesForDoctorViewModel,
                             val doctorId : Int, val date : String) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDeleteQueueBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private val viewModel by viewModels<DeleteQueueForDoctorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetDeleteQueueBinding.inflate(inflater, container, false)
        stateObserve()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDismiss.setOnClickListener { dismiss() }
        binding.btnDelete.setOnClickListener { viewModel.deleteQueue(queueId) }

        actionResultDialog.replayBtnClickListener { viewModel.deleteQueue(queueId) }

    }

    private fun stateObserve() {
        viewModel.deleteQueueState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state->

                when(state) {
                    is PageState.Init -> Unit

                    is PageState.IsLoading -> actionResultDialog.showDialog("",
                        ActionResultSetAnimation.LOADING, state.loadingMessage)

                    is PageState.IsError -> actionResultDialog.showDialog(state.refreshType,
                        ActionResultSetAnimation.ERROR, state.errorMessage)

                    is PageState.IsSuccess -> {
                        actionResultDialog.showDialog("", ActionResultSetAnimation.SUCCESS, state.successMessage)
                        object : CountDownTimer(2500, 2500) {
                            override fun onTick(millisUntilFinished: Long) {}
                            override fun onFinish() {
                                queueForDoctorViewModel.getQueuesForDoctor(doctorId, date)
                                dismiss()
                            }
                        }.start()
                    }
                }
            }.launchIn(lifecycleScope)
    }

}