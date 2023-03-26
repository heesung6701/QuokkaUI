package com.github.heesung6701.quokkaui.anchor.window

import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type.statusBars

typealias Strategy = () -> WindowManager.LayoutParams

class WindowAnchorManager(val window: Window) {

    companion object {
        var DEBUG = false
        val TAG = WindowAnchorManager::class.simpleName
    }

    private var strategy: Strategy? = null

    private val layoutChangeListener = OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        Log.d(TAG, "OnLayoutChangeListener")
        relocate()
    }

    var register: Pair<Runnable, Runnable>? = null

    init {
        window.callback = object : WindowCallbackWrapper(window.callback) {
            override fun onContentChanged() {
                super.onContentChanged()
                relocate()
            }

            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                register?.first?.run()
            }

            override fun onDetachedFromWindow() {
                super.onDetachedFromWindow()
                register?.second?.run()
            }
        }
    }

    fun attach(anchor: View) {
        strategy = {
            val statusBarHeight = ViewCompat.getRootWindowInsets(anchor)?.getInsets(statusBars())?.top?:0
            window.attributes.apply {
                val rect = Rect().apply {
                    anchor.getGlobalVisibleRect(this)
                }
                x = rect.centerX() - window.decorView.width / 2
                y = rect.bottom - statusBarHeight
                gravity = Gravity.LEFT or Gravity.TOP
            }
        }
        register?.second?.run()
        register = Pair(
            Runnable {
                anchor.addOnLayoutChangeListener(layoutChangeListener)
            },
            Runnable {
                anchor.removeOnLayoutChangeListener(layoutChangeListener)
            }
        )
        relocate()
    }

    private fun relocate() {
        val attrs = strategy?.invoke() ?: return
        debug("call relocate attrs: $attrs")
        window.attributes = attrs
    }

    private fun debug(message: String) {
        if (DEBUG) {
            Log.d(TAG, message)
        }
    }
}