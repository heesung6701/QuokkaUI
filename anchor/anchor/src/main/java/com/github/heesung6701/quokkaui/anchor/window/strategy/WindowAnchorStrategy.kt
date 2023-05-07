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
        val pos = if (isShowAboveAnchorView) {
            showAsAbove(window, anchor)
        } else {
            showAsBelow(window, anchor)
        }
        return {
            x = (anchor.centerX() - window.width() / 2)
                .coerceIn(root.left, root.right - window.width())
            y = (anchor.bottom - getRootRect().top)
                .coerceIn(root.top, root.bottom - window.height() - anchor.height())
            gravity = Gravity.LEFT or Gravity.TOP
            this
        }
    }

    private fun showAsAbove(window: Rect, anchor: Rect): Pair<Int, Int> {
        val x = (anchor.centerX() - window.width() / 2)
        val y = (anchor.top - window.height())
        return Pair(x, y)
    }

    private fun showAsBelow(window: Rect, anchor: Rect): Pair<Int, Int> {
        val x = (anchor.centerX() - window.width() / 2)
        val y = anchor.bottom
        return Pair(x, y)
    }

    interface Parameters {
        fun getWindowRect(): Rect

        fun getAnchorRect(): Rect

        fun getRootRect(): Rect
    }

    data class ParametersImpl(private val window: Window, private val anchor: View) : Parameters {

        override fun getWindowRect(): Rect = Rect().apply {
            window.decorView.getGlobalVisibleRect(this)
        }

        override fun getAnchorRect(): Rect = Rect().apply {
            anchor.getGlobalVisibleRect(this)
        }

        override fun getRootRect(): Rect {
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

        private fun getSystemInsets(): Insets {
            return ViewCompat.getRootWindowInsets(anchor)
                ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: Insets.NONE
        }
    }
}