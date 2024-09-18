package com.arfomax.onmed.presentation.ui.fragments.diagnostics.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.diagnostics.model.DiagnosticsModel
import com.arfomax.onmed.databinding.ItemDiagnosticsBinding
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class DiagnosticsAdapter : RecyclerView.Adapter<DiagnosticsAdapter.ViewHolder>() {

    private var list = arrayListOf<DiagnosticsModel>()
    private var diagnosticClickListener: ((DiagnosticsModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: ArrayList<DiagnosticsModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun diagnosticClickListener(listener: ((DiagnosticsModel) -> Unit)) {
        diagnosticClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemDiagnosticsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBine(item: DiagnosticsModel) {

            Glide.with(binding.root).load(Constants.BASE_URL_IMAGES + item.images[0].file)
                .error(R.drawable.logo_onmed).into(binding.imgDiagnosticImage)

            binding.tvDiagnosticName.text = item.diagnosticsName
            binding.tvDiagnosticSpeciality.text = "Tekshiruv turi : ${item.inspectionsCount} ta"
            binding.tvDiagnosticAddress.text = "${item.region} -> ${item.district}"

            binding.btnDiagnosticInfo.setOnClickListener {
                diagnosticClickListener?.invoke(item)
            }

            if (layoutPosition+1 == list.size) binding.root.margin(bottom = 125f)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDiagnosticsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBine(item = list[position])
    }

}