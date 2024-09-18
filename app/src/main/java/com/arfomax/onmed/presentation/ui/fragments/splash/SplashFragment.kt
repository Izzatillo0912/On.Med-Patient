package com.arfomax.onmed.presentation.ui.fragments.splash

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arfomax.onmed.R
import com.arfomax.onmed.databinding.FragmentSplashBinding
import com.arfomax.onmed.presentation.utils.Constants
import com.orhanobut.hawk.Hawk

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        object : CountDownTimer(2500, 2500) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (Hawk.get<Boolean>(Constants.INTERVIEW) == true) {
                    findNavController().setGraph(R.navigation.main_nav_graph)
                }
                else findNavController().navigate(R.id.action_splashFragment_to_interviewFragment)
            }
        }.start()

    }
}