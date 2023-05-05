package com.github.heesung6701.quokkaui.anchor.window.strategy

import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class WindowAnchorStrategy(private val anchorParams: Parameters) : AbstractAnchorStrategy() {

    final override fun reducer(): WindowManager.LayoutParams.() -> WindowManager.LayoutParams {
        return anchorParams.reducer()
    }

    open fun Parameters.reducer(): WindowManager.LayoutParams.() -> WindowManager.LayoutParams {
        val anchor = getAnchorRect()
        val window = getWindowRect()
        val root = getRootRect()

        val isShowAboveAnchorView = anchor.bottom + window.height() > root.bottom
        val offset = if (isShowAboveAnchorView) {
            -window.height() - anchor.height()
        } else {
            0
        }

        return {
            x = (anchor.centerX() - window.width() / 2)
                .coerceIn(root.left, root.right - window.width())
            y = (anchor.bottom + offset - getSystemInsets().top)
                .coerceIn(root.top, root.bottom - window.height())
            gravity = Gravity.LEFT or Gravity.TOP
            this
        }
    }

    data class Parameters(private val window: Window, private val anchor: View) {

        fun getWindowRect(): Rect = Rect().apply {
            window.decorView.getGlobalVisibleRect(this)
        }

        fun getAnchorRect(): Rect = Rect().apply {
            anchor.getGlobalVisibleRect(this)
        }

        fun getRootRect(): Rect {
            val rootRect = Rect().apply {
                anchor.rootView.getGlobalVisibleRect(this)
            }
            val insets = getSystemInsets()
            rootRect.top += insets.top
            rootRect.bottom -= insets.bottom
            rootRect.left += insets.left
            rootRect.right -= insets.right
            return rootRect
        }

        fun getSystemInsets(): Insets {
            return ViewCompat.getRootWindowInsets(anchor)
                ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: Insets.NONE
        }
    }
}