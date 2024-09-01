package com.arfomax.onmed.presentation.ui.fragments.interview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentInterviewBinding

class InterviewFragment : Fragment() {

    private lateinit var binding: FragmentInterviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInterviewBinding.inflate(inflater, container, false)
        return binding.root
    }
}