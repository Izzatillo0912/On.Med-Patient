package com.arfomax.onmed.presentation.ui.utils

import android.graphics.Rect
import android.view.View

class KeyboardVisibilityListener(
    private val rootView: View,
    private val onKeyboardVisibilityChanged: (Boolean) -> Unit
) {

    private var isKeyboardVisible = false

    init {
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)

            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom

            val isVisible = keypadHeight > screenHeight * 0.15 // 15% dan katta bo'lsa, klaviatura ochiq deb hisoblaymiz
            if (isVisible != isKeyboardVisible) {
                isKeyboardVisible = isVisible
                onKeyboardVisibilityChanged(isVisible) // Hodisani chaqiramiz
            }
        }
    }
}