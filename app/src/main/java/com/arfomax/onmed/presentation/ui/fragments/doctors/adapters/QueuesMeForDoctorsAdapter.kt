package com.arfomax.onmed.presentation.ui.fragments.doctors.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.queueForDoctor.model.QueueModel
import com.arfomax.onmed.data.network.queueForDoctor.model.QueuesModel
import com.arfomax.onmed.databinding.ItemMyQueuesForDoctorsBinding
import com.arfomax.onmed.domain.common.BaseResult
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class QueuesMeForDoctorsAdapter : RecyclerView.Adapter<QueuesMeForDoctorsAdapter.ViewHolder>() {

    private var list = arrayListOf<QueueModel>()
    private var queueClickListener : ((QueueModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList : ArrayList<QueueModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun queueForDoctorClickListener(listener : ((QueueModel) -> Unit)) {
        queueClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemMyQueuesForDoctorsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBine(item : QueueModel) {

            Glide.with(binding.root.context).load(Constants.BASE_URL_IMAGES + item.doctor.photo)
                .error(R.drawable.logo_onmed).into(binding.imgDoctorImage)

            binding.tvDoctorName.text = item.doctor.firstName + " " + item.doctor.lastName
            binding.tvDoctorSpeciality.text = item.doctor.speciality?.name ?: "Belgilanmagan"

            binding.myQueueForDoctor.setOnClickListener {
                queueClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMyQueuesForDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBine(item = list[position])
    }
}