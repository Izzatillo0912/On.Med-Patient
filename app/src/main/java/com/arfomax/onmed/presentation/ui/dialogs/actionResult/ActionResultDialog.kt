package com.arfomax.onmed.presentation.ui.dialogs.actionResult

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import com.arfomax.doctorOnMed.presentation.ui.dialog.actionResult.ActionResultSetAnimation
import com.arfomax.onmed.databinding.DialogActionResultBinding
import com.arfomax.onmed.presentation.ui.utils.SetMargin.margin

class ActionResultDialog(val fragment: Fragment) : Dialog(fragment.requireContext()) {

    private lateinit var binding: DialogActionResultBinding
    private val actionResultSetAnimation = ActionResultSetAnimation()

    private var replayClickListener : ((String) -> Unit)? = null
    private var dismissClickListener : ((String) -> Unit)? = null

    fun replayBtnClickListener(listener : ((String)-> Unit)) {
        replayClickListener = listener
    }

    fun dismissBtnClickListener(listener : ((String)-> Unit)) {
        dismissClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogActionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        binding.root.margin(left = 30f, right = 30f)
        setCancelable(false)

        actionResultSetAnimation.setButtonVisibility(binding.btnDismiss, binding.btnRetry)
    }

    fun showDialog(refreshType : String, animType : String, message : String) {
        create(); show()

        binding.responseMessage.text = message

        binding.btnRetry.setOnClickListener {
            replayClickListener?.invoke(refreshType)
        }
        binding.btnDismiss.setOnClickListener {
            dismiss()
            dismissClickListener?.invoke(refreshType)
        }

        if (message == ActionResultMessage.TIME_OUT)
            actionResultSetAnimation.setAnimation(ActionResultSetAnimation.NO_INTERNET, binding.responseAnim)
        else actionResultSetAnimation.setAnimation(animType, binding.responseAnim)

        if (animType == ActionResultSetAnimation.SUCCESS) {
            object : CountDownTimer(2000, 2000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    dismiss()
                }
            }.start()
        }
    }
}