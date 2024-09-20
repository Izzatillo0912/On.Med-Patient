package com.arfomax.onmed.presentation.ui.fragments.diagnosticQueues

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.diagnosticInfo.model.InspectionDoctorModel
import com.arfomax.onmed.databinding.ItemSelectedDoctorBinding
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class InspectionDoctorsAdapter : RecyclerView.Adapter<InspectionDoctorsAdapter.ViewHolder>() {

    private var list = arrayListOf<InspectionDoctorModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newDoctors : ArrayList<InspectionDoctorModel>) {
        list = newDoctors
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSelectedDoctorBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item : InspectionDoctorModel) {

            Glide.with(binding.root.context).load(Constants.BASE_URL_IMAGES + item.photo)
                .error(R.drawable.logo_onmed).into(binding.doctorImage)

            binding.doctorName.text = item.firstName + " " + item.lastName
            binding.doctorSpecialityName.text = item.speciality.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSelectedDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}