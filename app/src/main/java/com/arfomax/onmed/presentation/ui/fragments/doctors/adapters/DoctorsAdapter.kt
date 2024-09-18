package com.arfomax.onmed.presentation.ui.fragments.doctors.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.databinding.ItemDoctorsBinding
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.MyPriceFormat
import com.bumptech.glide.Glide

class DoctorsAdapter : RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    private var list = arrayListOf<DoctorInfoModel>()
    private var doctorClickListener: ((DoctorInfoModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: ArrayList<DoctorInfoModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun doctorClickListener(listener: ((DoctorInfoModel) -> Unit)) {
        doctorClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemDoctorsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBine(item: DoctorInfoModel) {

            Glide.with(binding.root).load(Constants.BASE_URL_IMAGES + item.photo)
                .error(R.drawable.logo_onmed).into(binding.imgDoctorImage)

            binding.tvDoctorName.text = item.firstName + " " + item.lastName
            binding.tvDoctorSpeciality.text = item.speciality?.name
            binding.tvDoctorPrice.text = "Ko'rik narxi : ${MyPriceFormat.formattedVolume(item.price)} so'm"

            binding.btnDoctorInfo.setOnClickListener {
                doctorClickListener?.invoke(item)
            }

            if (layoutPosition+1 == list.size) binding.root.margin(bottom = 125f)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBine(item = list[position])
    }

}