package com.arfomax.onmed.presentation.ui.fragments.diagnosticQueues

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.databinding.FragmentDiagnosticQueuesBinding
import com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForInspection.AddQueueForInspectionBottomSheet
import com.arfomax.onmed.presentation.ui.bottomSheets.deleteQueueForInspection.DeleteQueueForInspectionBottomSheet
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.diagnosticQueues.viewModels.QueuesForInspectionViewModel
import com.arfomax.onmed.presentation.ui.fragments.doctorQueues.QueuesAdapter
import com.arfomax.onmed.presentation.utils.MyPriceFormat
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.arfomax.onmed.presentation.utils.dateForDoctor.GetCurrentDayAndFourthMonthEndDay
import com.arfomax.onmed.presentation.utils.dateForDoctor.GetDateFormat
import com.arfomax.onmed.presentation.utils.dateForInspection.DateFormatToSpinner
import com.arfomax.onmed.presentation.utils.dateForInspection.GetMonths
import com.arfomax.onmed.presentation.utils.dateForInspection.GetWorkingDays
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DiagnosticQueuesFragment : Fragment() {

    private lateinit var binding : FragmentDiagnosticQueuesBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private val queuesForInspectionViewModel : QueuesForInspectionViewModel by viewModels()
    private val queuesAdapter = QueuesAdapter()
    private val inspectionDoctorsAdapter = InspectionDoctorsAdapter()
    private var workDayInWeak : String = RuntimeCache.combineInspection?.workday ?: ""
    private var selectedDate : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiagnosticQueuesBinding.inflate(inflater, container, false)

        binding.rvQueues.adapter = queuesAdapter
        binding.rvSelectedDoctors.adapter = inspectionDoctorsAdapter

        setData()
        stateObserve()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.spinnerMonth.setOnSpinnerItemSelectedListener(
            OnSpinnerItemSelectedListener<String> { _, _, _, _ ->
                binding.spinnerDay.invalidate()
                val days = GetWorkingDays.getDaysInMonth(binding.spinnerMonth.text.toString(), workDayInWeak)
                binding.spinnerDay.setItems(days)
                if (days.isNotEmpty()) binding.spinnerDay.selectItemByIndex(0)
            })

        binding.spinnerDay.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String>{
                _, _, _, newItem ->
            selectedDate = GetDateFormat.getDateFromMonthAndWorkDay(binding.spinnerMonth.text.toString(), newItem)
            queuesForInspectionViewModel.getQueuesForInspections(RuntimeCache.combineInspection?.id ?: 0, selectedDate)
        })

        binding.btnAddQueue.setOnClickListener {
            Toast.makeText(requireContext(), selectedDate, Toast.LENGTH_SHORT).show()
            AddQueueForInspectionBottomSheet(queuesForInspectionViewModel,
                RuntimeCache.combineInspection?.id ?: 0, selectedDate)
            .show(childFragmentManager, "AddQueueForInspectionBottomSheet")
        }

        queuesAdapter.deleteClickListener {
            DeleteQueueForInspectionBottomSheet(it.id, queuesForInspectionViewModel, it.diagnosticsInspection.id,
                selectedDate).show(childFragmentManager, "DeleteQueueForInspectionBottomSheet")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {

        binding.tvInspectionPrice.text = "Tekshiruv narxi :" +
                " ${MyPriceFormat.formattedVolume(RuntimeCache.combineInspection?.price?:0.0)} so'm"
        binding.tvTitle.text = RuntimeCache.combineInspection?.inspection?.name ?: "Navbatlar"
        binding.workDay.text = "Ish kunlari : ${RuntimeCache.combineInspection?.workday ?: "Belgilanmagan"}"
        binding.workTime.text = "Ish vaqti : ${RuntimeCache.combineInspection?.startWorkTime?.dropLast(3)} dan" +
                " - ${RuntimeCache.combineInspection?.endWorkTime?.dropLast(3)} gacha"
        binding.eatingTime.text = "Abet vaqti : ${RuntimeCache.combineInspection?.startLunchTime?.dropLast(3)} dan" +
                " - ${RuntimeCache.combineInspection?.endLunchTime?.dropLast(3)} gacha"
        inspectionDoctorsAdapter.submitList(RuntimeCache.combineInspection?.doctors?: arrayListOf())

        binding.spinnerMonth.setItems(GetMonths.generateNextThreeMonths())
        binding.spinnerMonth.selectItemByIndex(0)

        workDayInWeak = RuntimeCache.combineInspection?.workday ?: ""
        val days = GetWorkingDays.getDaysInMonth(binding.spinnerMonth.text.toString(), workDayInWeak)
        binding.spinnerDay.setItems(days)
        if (days.isNotEmpty()) binding.spinnerDay.selectItemByIndex(0)

        if (RuntimeCache.myQueueDate.isEmpty()) {
            val monthName = GetMonths.generateNextThreeMonths()[0]
            selectedDate = GetDateFormat.getDateFromMonthAndWorkDay(monthName,
                GetWorkingDays.getDaysInMonth(monthName, workDayInWeak)[0])
        } else {
            selectedDate = RuntimeCache.myQueueDate
            RuntimeCache.myQueueDate = ""
            binding.spinnerMonth.text = DateFormatToSpinner.getMonthName(selectedDate)
            binding.spinnerDay.text = DateFormatToSpinner.day(selectedDate)
            queuesForInspectionViewModel.getQueuesForInspections(RuntimeCache.combineInspection?.id ?: 0, selectedDate)
        }
    }

    private fun stateObserve() {

        queuesForInspectionViewModel.queuesForInspectionState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setStateActionResultDialog1(state) }.launchIn(lifecycleScope)
    }

    private fun setStateActionResultDialog1(state : PageState) {
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
                val data = state.data as QueuesModel
                queuesAdapter.submitList(data.results)
            }
        }
    }
}