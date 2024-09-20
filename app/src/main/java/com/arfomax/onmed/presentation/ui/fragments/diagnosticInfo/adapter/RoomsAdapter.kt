package com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfomax.onmed.R
import com.arfomax.onmed.data.network.diagnosticInfo.model.DiagnosticsRoom
import com.arfomax.onmed.databinding.ItemDiagnosticRoomBinding
import com.arfomax.onmed.presentation.utils.Constants
import com.bumptech.glide.Glide

class RoomsAdapter : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    private var roomsList = arrayListOf<DiagnosticsRoom>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newRoomsList : ArrayList<DiagnosticsRoom>) {
        roomsList = newRoomsList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemDiagnosticRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(diagnosticsRoom: DiagnosticsRoom) {

            Glide.with(binding.root.context).load(Constants.BASE_URL_IMAGES + diagnosticsRoom.roomFiles[0].file)
                .error(R.drawable.logo_onmed).into(binding.imageView)

            binding.roomName.text = diagnosticsRoom.name
            binding.roomDetail.text = diagnosticsRoom.detail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDiagnosticRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = roomsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(roomsList[position])
    }
}