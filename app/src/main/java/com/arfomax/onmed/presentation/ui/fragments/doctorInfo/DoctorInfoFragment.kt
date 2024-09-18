package com.arfomax.onmed.presentation.ui.fragments.doctorInfo

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
import com.arfomax.onmed.data.network.getWorkDays.GetWorkDay
import com.arfomax.onmed.databinding.FragmentDoctorInfoBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.doctorInfo.viewModel.GetWorkDaysViewModel
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.MyPriceFormat
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.arfomax.onmed.presentation.utils.dateForDoctor.GetCurrentDayAndFourthMonthEndDay
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DoctorInfoFragment : Fragment() {

    private lateinit var binding: FragmentDoctorInfoBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private val getWorkDaysViewModel by viewModels<GetWorkDaysViewModel>()
    private val doctorInfo = RuntimeCache.doctorInfoModel
    private val getCalendar = GetCurrentDayAndFourthMonthEndDay.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionResultDialog = ActionResultDialog(this)
        RuntimeCache.doctorWorkDays.clear()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoctorInfoBinding.inflate(inflater, container, false)

        doctorInfo?.let { doctor ->

            Glide.with(binding.root).load(Constants.BASE_URL_IMAGES + doctor.photo)
                .error(R.drawable.logo_onmed).into(binding.doctorImage)

            binding.doctorName.text = doctor.firstName + " " + doctor.lastName
            binding.tvSpeciality.text = doctor.speciality?.name ?: "Belgilanmagan"
            binding.tvPrice.text = "Ko'rik narxi : ${MyPriceFormat.formattedVolume(doctor.price)} so'm"
            binding.tvAddress.text = "${doctor.region.nameUz} ${doctor.district.nameUz}\n${doctor.address}"
            binding.tvDoctorInfo.text = doctor.description
        }
        stateObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getWorkDaysViewModel.getWorkDays(doctorInfo?.id?:0, getCalendar[1].toString(), getCalendar[2].toString())

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnDoctorQueues.setOnClickListener {
            findNavController().navigate(R.id.action_doctorInfoFragment_to_doctorQueuesFragment)
        }

        binding.btnGoMap.setOnClickListener { Toast.makeText(requireContext(), "Tez kunda :)", Toast.LENGTH_SHORT).show() }

        actionResultDialog.replayBtnClickListener {
            getWorkDaysViewModel.getWorkDays(doctorInfo?.id?:0, getCalendar[1].toString(), getCalendar[2].toString())
        }
    }

    private fun stateObserver() {
        getWorkDaysViewModel.getWorDaysState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state-> setStateActionResultDialog(state) }.launchIn(lifecycleScope)
    }

    private fun setStateActionResultDialog(state: PageState) {
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
                RuntimeCache.doctorWorkDays.clear()
                for (i in state.data as ArrayList<GetWorkDay>)
                    RuntimeCache.doctorWorkDays.add(i.workDay)
            }
        }
    }
}