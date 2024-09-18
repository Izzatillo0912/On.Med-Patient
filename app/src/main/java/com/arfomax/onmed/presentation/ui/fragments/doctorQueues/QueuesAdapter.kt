package com.arfomax.onmed.presentation.ui.fragments.doctorQueues

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.queueForDoctor.model.QueueModel
import com.arfomax.onmed.databinding.ItemQueuesBinding
import com.arfomax.onmed.presentation.utils.RuntimeCache

class QueuesAdapter : RecyclerView.Adapter<QueuesAdapter.ViewHolder>() {

    private var queuesList = arrayListOf<QueueModel>()
    private var deleteListener : ((QueueModel) -> Unit)? = null
    private var visibleBtn = true

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newQueuesList : ArrayList<QueueModel>) {
        queuesList = newQueuesList
        notifyDataSetChanged()
    }

    fun deleteClickListener(listener : ((QueueModel) -> Unit)) {
        deleteListener = listener
    }

    inner class ViewHolder(private val binding: ItemQueuesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item : QueueModel) {
            binding.tvQueueNumber.text = "${layoutPosition + 1}"
            binding.queueName.text = item.fio

            binding.btnDeleteQueue.setOnClickListener {
                deleteListener?.invoke(item)
            }

            if (item.patientId == RuntimeCache.userId) {
                binding.btnDeleteQueue.visibility = ViewGroup.VISIBLE
                binding.root.setCardBackgroundColor(binding.root.context.getColor(R.color.btn_blue))
                binding.queueName.setTextColor(binding.root.context.getColor(R.color.white))
                binding.queueTime.setTextColor(binding.root.context.getColor(R.color.white))
            } else {
                binding.btnDeleteQueue.visibility = ViewGroup.GONE
                binding.root.setCardBackgroundColor(binding.root.context.getColor(R.color.white))
                binding.queueName.setTextColor(binding.root.context.getColor(R.color.black))
                binding.queueTime.setTextColor(binding.root.context.getColor(R.color.black))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemQueuesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = queuesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(queuesList[position])
    }
}