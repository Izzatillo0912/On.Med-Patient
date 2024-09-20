package com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticImageModel
import com.arfomax.onmed.databinding.ItemDiagnosticImageBinding
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class ImagesViewPagerAdapter(private val discountList : List<DiagnosticImageModel>) :
 RecyclerView.Adapter<ImagesViewPagerAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemDiagnosticImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item : DiagnosticImageModel) {
            Glide.with(binding.root).load(Constants.BASE_URL_IMAGES + item.file).into(binding.diagnosticImageItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDiagnosticImageBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount() = discountList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(discountList[position])
    }
}