package com.arfomax.onmed.presentation.ui.dialogs.selectRegion

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import android.widget.TextView
import com.arfomax.onmed.databinding.DialogSelectRegionBinding
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener

class SelectRegionDialog(context : Context) : Dialog(context) {

    private lateinit var binding: DialogSelectRegionBinding
    private var regionsNames : List<String> = RuntimeCache.regionsAndDistrictsList.map { it.nameUz }
    private var districtNames : List<String> = RuntimeCache.regionsAndDistrictsList[0].districts.map { it.nameUz }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogSelectRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        binding.root.margin(left = 20f, right = 20f)
    }

    @SuppressLint("SetTextI18n")
    fun showDialog(textView : TextView) { create(); show()

        if (RuntimeCache.userRegionName.isEmpty() && RuntimeCache.userDistrictName.isEmpty()) {
            RuntimeCache.userRegionName = regionsNames[0]
            RuntimeCache.userDistrictName = districtNames[0]
            RuntimeCache.userRegionId = RuntimeCache.regionsAndDistrictsList[0].id
            RuntimeCache.userDistrictId = RuntimeCache.regionsAndDistrictsList[0].districts[0].id
        }

        binding.btnSelect.setOnClickListener {
            textView.text = RuntimeCache.userRegionName + " -> " + RuntimeCache.userDistrictName
            dismiss()
        }
        binding.btnDismiss.setOnClickListener { dismiss() }

        binding.spinnerRegion.setItems(regionsNames)
        binding.spinnerDistrict.setItems(districtNames)

        binding.spinnerRegion.selectItemByIndex(0)
        binding.spinnerDistrict.selectItemByIndex(0)

        binding.spinnerRegion.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> {
                _, _, _, newItem ->
            RuntimeCache.regionsAndDistrictsList.forEach { region ->
                if (region.nameUz == newItem) {
                    districtNames = region.districts.map { it.nameUz }
                    binding.spinnerDistrict.setItems(districtNames)
                    binding.spinnerDistrict.selectItemByIndex(0)
                    RuntimeCache.userRegionName = newItem
                    RuntimeCache.userRegionId = region.id
                    RuntimeCache.userDistrictName = region.districts[0].nameUz
                    RuntimeCache.userDistrictId = region.districts[0].id
                }
            }
        })

        binding.spinnerDistrict.setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> {
            _,_,_, newItem ->

            RuntimeCache.regionsAndDistrictsList.forEach { region ->
                region.districts.forEach { district ->
                    if (district.nameUz == newItem) {
                        RuntimeCache.userDistrictName = newItem
                        RuntimeCache.userDistrictId = district.id
                    }
                }
            }
        })
    }

}