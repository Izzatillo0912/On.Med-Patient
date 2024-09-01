package com.arfomax.onmed.presentation.ui.fragments.doctorQueues

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentDoctorQueuesBinding

class DoctorQueuesFragment : Fragment() {

    private lateinit var binding: FragmentDoctorQueuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoctorQueuesBinding.inflate(inflater, container, false)
        return binding.root
    }
}