package com.arfomax.onmed.presentation.ui.fragments.doctors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentDoctorsBinding

class DoctorsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoctorsBinding.inflate(inflater, container, false)
        return binding.root
    }
}