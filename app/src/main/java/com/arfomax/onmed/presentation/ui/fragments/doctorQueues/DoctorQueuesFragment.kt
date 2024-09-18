package com.arfomax.onmed.presentation.ui.fragments.doctorQueues

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.getWorkDays.GetWorkDay
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.databinding.FragmentDoctorQueuesBinding
import com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForDoctor.AddQueueForDoctorBottomSheet
import com.arfomax.onmed.presentation.ui.bottomSheets.deleteQueueForDoctor.DeleteQueueBottomSheet
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.doctorInfo.viewModel.GetWorkDaysViewModel
import com.arfomax.onmed.presentation.ui.fragments.doctorQueues.viewModel.QueuesForDoctorViewModel
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.MyPriceFormat
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.arfomax.onmed.presentation.utils.dateForDoctor.GetCurrentDayAndFourthMonthEndDay
import com.arfomax.onmed.presentation.utils.dateForDoctor.GetWorkingDaysForSpinner
import com.orhanobut.hawk.Hawk
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DoctorQueuesFragment : Fragment() {

    private lateinit var binding: FragmentDoctorQueuesBinding
    private lateinit var actionResultDialog1: ActionResultDialog
    private lateinit var actionResultDialog2: ActionResultDialog
    private val queuesAdapter = QueuesAdapter()
    private val queuesForDoctorViewModel by viewModels<QueuesForDoctorViewModel>()
    private val getSelectedWorkDaysViewModel by viewModels<GetWorkDaysViewModel>()
    private val getCalendar = GetCurrentDayAndFourthMonthEndDay.get()
    private var selectedDate : String =
        RuntimeCache.myQueueDate.ifEmpty { GetCurrentDayAndFourthMonthEndDay.get().getValue(1).toString() }
    private var doctorId = RuntimeCache.doctorInfoModel?.id ?: 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog1 = ActionResultDialog(this)
        actionResultDialog2 = ActionResultDialog(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoctorQueuesBinding.inflate(inflater, container, false)
        binding.rvQueues.adapter = queuesAdapter
        binding.tvDoctorPrice.text = "Ko'rik narxi : ${MyPriceFormat.formattedVolume(
            RuntimeCache.doctorInfoModel?.price ?: 0.0)} so'm"

        if (RuntimeCache.doctorWorkDays.isEmpty())
            getSelectedWorkDaysViewModel.getWorkDays(doctorId, getCalendar[1].toString(), getCalendar[2].toString())
        else {
            binding.spinnerDay.setItems(GetWorkingDaysForSpinner.formatDates(RuntimeCache.doctorWorkDays))
            binding.spinnerDay.selectItemByIndex(0)
        }

        stateObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queuesForDoctorViewModel.getQueuesForDoctor(doctorId, selectedDate)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.spinnerDay.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String>{
                _, _, _, newItem ->
            selectedDate = GetWorkingDaysForSpinner.parseDate(newItem)
            queuesForDoctorViewModel.getQueuesForDoctor(doctorId, selectedDate)
        })

        binding.btnAddQueue.setOnClickListener {
            if (Hawk.get<Boolean>(Constants.USER_VERIFIED) == true) {
                AddQueueForDoctorBottomSheet(queuesForDoctorViewModel, doctorId, selectedDate)
                    .show(childFragmentManager, "AddQueueForDoctorBottomSheet")
            } else findNavController().navigate(R.id.action_doctorQueuesFragment_to_loginFragment)

        }

        queuesAdapter.deleteClickListener {
            DeleteQueueBottomSheet(it.id, queuesForDoctorViewModel, doctorId, selectedDate)
                .show(childFragmentManager, "DeleteQueueBottomSheet")
        }

        actionResultDialog1.replayBtnClickListener {
            getSelectedWorkDaysViewModel.getWorkDays(doctorId, getCalendar[1].toString(), getCalendar[2].toString())
        }

        actionResultDialog2.replayBtnClickListener {
            queuesForDoctorViewModel.getQueuesForDoctor(doctorId, selectedDate)
        }
    }

    private fun stateObserver() {

        getSelectedWorkDaysViewModel.getWorDaysState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> setStateActionResultDialog1(state) }.launchIn(lifecycleScope)

        queuesForDoctorViewModel.queuesForMeState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> setStateActionResultDialog2(state) }.launchIn(lifecycleScope)
    }

    private fun setStateActionResultDialog1(state : PageState) {
        when(state) {

            is PageState.Init -> Unit

            is PageState.IsLoading -> {
                actionResultDialog1.showDialog("", ActionResultSetAnimation.LOADING, state.loadingMessage)
            }

            is PageState.IsError -> {
                actionResultDialog1.showDialog(state.refreshType, ActionResultSetAnimation.ERROR, state.errorMessage)
            }

            is PageState.IsSuccess -> {
                actionResultDialog1.dismiss()
                for (i in state.data as ArrayList<GetWorkDay>) RuntimeCache.doctorWorkDays.add(i.workDay)
                binding.spinnerDay.setItems(GetWorkingDaysForSpinner.formatDates(RuntimeCache.doctorWorkDays))
                if (RuntimeCache.doctorWorkDays.isNotEmpty()) {
                    binding.spinnerDay.selectItemByIndex(0)
                    RuntimeCache.doctorWorkDays.forEachIndexed { index, date ->
                        if (date == RuntimeCache.myQueueDate) binding.spinnerDay.selectItemByIndex(index)
                    }
                }
                else Toast.makeText(requireContext(), "Ish kunlari belgilanmagan!!", Toast.LENGTH_SHORT).show()
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
                queuesAdapter.submitList(data.results)
            }
        }
    }

}