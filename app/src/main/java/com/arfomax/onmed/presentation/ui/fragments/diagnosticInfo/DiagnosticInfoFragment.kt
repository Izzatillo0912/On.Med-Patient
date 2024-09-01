package com.arfomax.onmed.presentation.ui.fragments.diagnosticInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentDiagnosticInfoBinding

class DiagnosticInfoFragment : Fragment() {

    private lateinit var binding: FragmentDiagnosticInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiagnosticInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}