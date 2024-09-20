package com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.diagnosticInfo.model.CombineInspection
import com.arfomax.onmed.data.network.diagnosticInfo.model.DiagnosticsRoom
import com.arfomax.onmed.data.network.diagnosticInfo.model.Inspection
import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticsModel
import com.arfomax.onmed.databinding.ItemDiagnosticRoomBinding
import com.arfomax.onmed.databinding.ItemInspectionBinding
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.MyPriceFormat
import com.bumptech.glide.Glide

class InspectionsAdapter : RecyclerView.Adapter<InspectionsAdapter.ViewHolder>() {

    private var inspectionsList = arrayListOf<CombineInspection>()
    private var itemClickListener: ((CombineInspection) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newInspectionsList : ArrayList<CombineInspection>) {
        inspectionsList = newInspectionsList
        notifyDataSetChanged()
    }

    fun itemClickListener(listener: ((CombineInspection) -> Unit)) {
        itemClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemInspectionBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: CombineInspection) {

            binding.inspectionName.text = item.inspection.name
            binding.inpsectionPrice.text = "${MyPriceFormat.formattedVolume(item.price)} so'm"
            binding.inspectionInfo.text = "${item.workday} ${item.inspection.info}"

            binding.btnInspectionQeueue.setOnClickListener { itemClickListener?.invoke(item) }

            if (layoutPosition+1 == inspectionsList.size) binding.root.margin(bottom = 60f)
            else binding.root.margin(bottom = 8f)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemInspectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = inspectionsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(inspectionsList[position])
    }
}