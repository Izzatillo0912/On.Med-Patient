package com.arfomax.onmed.presentation.ui.fragments.interview

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentInterviewBinding
import com.arfomax.onmed.presentation.utils.Constants
import com.orhanobut.hawk.Hawk

class InterviewFragment : Fragment() {

    private lateinit var binding: FragmentInterviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInterviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            Hawk.put(Constants.INTERVIEW, true)
            findNavController().setGraph(R.navigation.main_nav_graph)
        }
    }
}