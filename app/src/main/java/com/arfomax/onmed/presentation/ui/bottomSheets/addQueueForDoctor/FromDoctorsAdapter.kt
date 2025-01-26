package com.arfomax.onmed.presentation.ui.bottomSheets.addQueueForDoctor

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.doctors.model.DoctorInfoModel
import com.arfomax.onmed.databinding.ItemSelectDoctorBinding
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class FromDoctorsAdapter : RecyclerView.Adapter<FromDoctorsAdapter.ViewHolder>() {

    private var doctors = arrayListOf<DoctorInfoModel>()

    private var itemClickListener : ((Int) -> Unit)? = null

    fun selectedDoctorListener(listener : ((Int) -> Unit)) {
        Log.e("LambadaUnit", "Function Open")
        itemClickListener = listener
        Log.e("LambadaUnit", "itemClickListener = listener")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newDoctors : ArrayList<DoctorInfoModel>, selectedDoctor : Int) {
        doctors = newDoctors

        for (i in doctors.withIndex()) {
            if (i.value.id == selectedDoctor) {
                i.value.selected = true
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSelectDoctorBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item : DoctorInfoModel) {

            binding.doctorName.text = "${item.firstName} ${item.lastName}"
            binding.doctorSpecialityName.text = item.speciality?.name
            binding.doctorAddress.text = "${item.region.nameUz} ${item.district.nameUz}"

            Glide.with(binding.root.context).load(Constants.BASE_URL_IMAGES + item.photo).into(binding.doctorImage)

            binding.selectDoctor.setOnClickListener {
                for (i in doctors.withIndex()) {
                    if (i.value.selected && i.index != layoutPosition) {
                        i.value.selected = false
                        notifyItemChanged(layoutPosition)
                    }
                }
                item.selected = !item.selected
                notifyItemChanged(layoutPosition)

                itemClickListener?.invoke(item.id)
            }

            if (item.selected) {
                binding.selectBox.setImageResource(R.drawable.ic_check)
            }
            else {
                binding.selectBox.setImageResource(R.drawable.ic_circle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSelectDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = doctors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(doctors[position])
    }
}