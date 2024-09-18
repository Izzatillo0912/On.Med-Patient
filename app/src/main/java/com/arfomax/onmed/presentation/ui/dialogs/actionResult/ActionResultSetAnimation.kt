package com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult

import android.widget.Button
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView

class ActionResultSetAnimation {

    companion object {
        const val NO_INTERNET = "noInternet"
        const val ERROR = "error"
        const val LOADING = "loading"
        const val SUCCESS = "success"
    }

    private lateinit var btnDismiss : Button
    private lateinit var btnRetry : Button

    fun setButtonVisibility(btnDismiss : Button, btnRetry : Button) {
        this@ActionResultSetAnimation.btnDismiss = btnDismiss
        this@ActionResultSetAnimation.btnRetry = btnRetry
    }

    fun setAnimation(animType : String, lottieAnimation: LottieAnimationView) {
        when(animType) {

            LOADING -> {
                buttonsVisible(false)
                lottieAnimation.setAnimation("anim_searching.json")
                lottieAnimation.loop(true)
                lottieAnimation.playAnimation()
            }

            NO_INTERNET -> {
                lottieAnimation.setAnimation("anim_no_internet.json")
                lottieAnimation.playAnimation()
                lottieAnimation.loop(true)
                buttonsVisible(true)
            }

            SUCCESS -> {
                lottieAnimation.setAnimation("anim_checked.json")
                lottieAnimation.playAnimation()
                lottieAnimation.loop(false)
                buttonsVisible(false)
            }

            ERROR -> {
                lottieAnimation.setAnimation("anim_error.json")
                lottieAnimation.playAnimation()
                lottieAnimation.loop(false)
                buttonsVisible(true)
            }
        }
    }

    private fun buttonsVisible(visible : Boolean) {
        btnDismiss.isVisible = visible
        btnRetry.isVisible = visible
    }

}