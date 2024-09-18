package com.arfomax.onmed.presentation.ui.fragments.diagnostics.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.myQueuesForDiagnostics.MyQueuesForDiagnosticsModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueueModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.databinding.ItemMyQueueForDiagnosticsBinding
import com.arfomax.onmed.databinding.ItemMyQueuesForDoctorsBinding
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class MyQueuesForDiagnosticsAdapter : RecyclerView.Adapter<MyQueuesForDiagnosticsAdapter.ViewHolder>() {

    private var list = arrayListOf<MyQueuesForDiagnosticsModel>()
    private var queueClickListener : ((MyQueuesForDiagnosticsModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList : ArrayList<MyQueuesForDiagnosticsModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun queueForDoctorClickListener(listener : ((MyQueuesForDiagnosticsModel) -> Unit)) {
        queueClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemMyQueueForDiagnosticsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBine(item : MyQueuesForDiagnosticsModel) {

            binding.tvDoctorName.text = item.diagnosticsInspection.inspection.name
            binding.tvDoctorSpeciality.text = item.diagnosticsInspection.inspection.name
            binding.tvQueueNumber.text = item.queueNumber.toString()

            binding.btnMyQueueForDiagnostic.setOnClickListener {
                queueClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMyQueueForDiagnosticsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBine(item = list[position])
    }
}