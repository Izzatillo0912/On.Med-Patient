package com.arfomax.onmed.presentation.ui.fragments.profile

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentProfileBinding
import com.arfomax.onmed.presentation.ui.activity.viewModels.RegionsAndDistrictsViewModel
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.orhanobut.hawk.Hawk
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private val regionsAndDistrictsViewModel by activityViewModels<RegionsAndDistrictsViewModel>()
    private val updateMeViewModel by viewModels<UpdateMeViewModel>()
    private var regionsNames : List<String> = RuntimeCache.regionsAndDistrictsList.map { it.nameUz }
    private var districtNames : List<String> = RuntimeCache.regionsAndDistrictsList[0].districts.map { it.nameUz }
    private var selectedRegionId = RuntimeCache.userRegionId
    private var selectedDistrictId = RuntimeCache.userDistrictId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.etName.setText(RuntimeCache.userName)
        binding.spinnerRegion.text = RuntimeCache.userRegionName
        binding.spinnerDistrict.text = RuntimeCache.userDistrictName

        updateMeResponseListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.requestFocus()

        if (RuntimeCache.regionsAndDistrictsList.isEmpty()) regionsAndDistrictsViewModel.getRegionsAndDistricts()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        regionsNames.forEach { regionName ->
            if (regionName == RuntimeCache.userRegionName)
                districtNames = RuntimeCache.regionsAndDistrictsList[regionsNames.indexOf(regionName)]
                    .districts.map { it.nameUz }
        }

        binding.spinnerRegion.setItems(regionsNames)
        binding.spinnerDistrict.setItems(districtNames)

        binding.spinnerRegion.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> {
                _, _, _, newItem ->
            RuntimeCache.regionsAndDistrictsList.forEach { region ->
                if (region.nameUz == newItem) {
                    districtNames = region.districts.map { it.nameUz }
                    binding.spinnerDistrict.setItems(districtNames)
                    binding.spinnerDistrict.selectItemByIndex(0)
                    selectedRegionId = region.id
                    selectedDistrictId = region.districts[0].id
                }
            }
        })

        binding.spinnerDistrict.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> {
                _,_,_, newItem ->

            RuntimeCache.regionsAndDistrictsList.forEach { region ->
                region.districts.forEach { district ->
                    if (district.nameUz == newItem) {
                        selectedDistrictId = district.id
                    }
                }
            }
        })

        binding.btnChange.setOnClickListener {
            if (binding.etName.text.isNotEmpty() && binding.etName.text.toString() != RuntimeCache.userName || selectedRegionId != RuntimeCache.userRegionId
                || selectedDistrictId != RuntimeCache.userDistrictId) {
                updateMeViewModel.updateMe(selectedRegionId, selectedDistrictId, binding.etName.text.toString())
            }else Toast.makeText(requireContext(), "O'zgartirish kiritilmagan", Toast.LENGTH_SHORT).show()

        }

        actionResultDialog.replayBtnClickListener {
            updateMeViewModel.updateMe(selectedRegionId, selectedDistrictId, binding.etName.text.toString())
        }
    }

    private fun updateMeResponseListener() {
        updateMeViewModel.updatePageState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state->
                when(state) {
                    is PageState.Init -> Unit
                    is PageState.IsLoading -> {
                        actionResultDialog.showDialog("", ActionResultSetAnimation.LOADING, state.loadingMessage)
                    }
                    is PageState.IsError -> {
                        actionResultDialog.showDialog(state.refreshType, ActionResultSetAnimation.ERROR, state.errorMessage)
                    }
                    is PageState.IsSuccess -> {
                        actionResultDialog.showDialog("", ActionResultSetAnimation.SUCCESS, state.data.toString())
                        Hawk.put(Constants.USER_NAME, binding.etName.text.toString())
                        Hawk.put(Constants.REGION_NAME, binding.spinnerRegion.text.toString())
                        Hawk.put(Constants.DISTRICT_NAME, binding.spinnerDistrict.text.toString())
                        Hawk.put(Constants.REGION_ID, selectedRegionId)
                        Hawk.put(Constants.DISTRICT_ID, selectedDistrictId)
                        RuntimeCache.userName = binding.etName.text.toString()
                        RuntimeCache.userRegionName = binding.spinnerRegion.text.toString()
                        RuntimeCache.userDistrictName = binding.spinnerDistrict.text.toString()
                        RuntimeCache.userRegionId = selectedRegionId
                        RuntimeCache.userDistrictId = selectedDistrictId
                    }

                }
            }.launchIn(lifecycleScope)
    }

    override fun onPause() {
        super.onPause()
        binding.spinnerRegion.dismiss()
        binding.spinnerDistrict.dismiss()
    }
}