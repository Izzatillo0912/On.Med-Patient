package com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo

import android.annotation.SuppressLint
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
import androidx.viewpager2.widget.ViewPager2
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.diagnosticInfo.model.DiagnosticInfoModel
import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticImageModel
import com.arfomax.onmed.databinding.FragmentDiagnosticInfoBinding
import com.arfomax.onmed.presentation.ui.dialogs.actionResult.ActionResultDialog
import com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo.adapter.ImagesViewPagerAdapter
import com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo.adapter.InspectionsAdapter
import com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo.adapter.RoomsAdapter
import com.arfomax.onmed.presentation.ui.utils.MyCustomIndicator
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DiagnosticInfoFragment : Fragment() {

    private lateinit var binding: FragmentDiagnosticInfoBinding
    private lateinit var actionResultDialog: ActionResultDialog
    private lateinit var myCustomIndicator: MyCustomIndicator
    private val roomsAdapter = RoomsAdapter()
    private val inspectionAdapter = InspectionsAdapter()
    private val diagnosticInfoViewModel by viewModels<DiagnosticInfoViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionResultDialog = ActionResultDialog(this)
        myCustomIndicator = MyCustomIndicator(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiagnosticInfoBinding.inflate(inflater, container, false)
        binding.rvRooms.adapter = roomsAdapter
        binding.rvInspections.adapter = inspectionAdapter
        stateObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        diagnosticInfoViewModel.getDiagnosticInfo(RuntimeCache.diagnosticsModel?.id ?: 0)

        binding.imagesViewpager.adapter = imagesAdapter()

        inspectionAdapter.itemClickListener {
            RuntimeCache.combineInspection = it
            findNavController().navigate(R.id.action_diagnosticInfoFragment_to_diagnosticQueuesFragment)
        }

        binding.imagesViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                myCustomIndicator.setUpCurrentIndicator(position, binding.indicatorLayout,
                    requireContext().getColor(R.color.btn_blue))
            }
        })
    }

    private fun imagesAdapter(): ImagesViewPagerAdapter {
        val discountsList = RuntimeCache.diagnosticsModel?.images

        binding.indicatorLayout.removeAllViews()
        binding.indicatorLayout.invalidate()

        myCustomIndicator.setUpIndicators(
            discountsList?.size ?: 0,
            binding.indicatorLayout,
            requireContext().getColor(R.color.btn_blue)
        )
        myCustomIndicator.setUpCurrentIndicator(
            0,
            binding.indicatorLayout,
            requireContext().getColor(R.color.btn_blue)
        )

        return ImagesViewPagerAdapter(discountsList?: arrayListOf())
    }

    private fun stateObserver() {

        diagnosticInfoViewModel.pageState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> setStateActionResultDialog(state) }.launchIn(lifecycleScope)

    }

    @SuppressLint("SetTextI18n")
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
                val data = state.data as DiagnosticInfoModel
                binding.diagnosticName.text = data.diagnosticsName
                binding.tvAddress.text = "${data.region.nameUz} -> ${data.district.nameUz}\n${data.address}"
                roomsAdapter.submitList(data.diagnosticsRooms)
                inspectionAdapter.submitList(data.combineInspections)
            }
        }
    }
}