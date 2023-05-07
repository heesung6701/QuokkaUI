package com.github.heesung6701.quokkaui.anchor.window

import android.util.Log
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import com.github.heesung6701.quokkaui.anchor.window.strategy.AbstractAnchorStrategy
import com.github.heesung6701.quokkaui.anchor.window.strategy.WindowAnchorStrategy
import java.util.concurrent.atomic.AtomicBoolean

class WindowAnchorConnector(val window: Window, val anchor: View) {

    companion object {
        var DEBUG = false
        val TAG = WindowAnchorConnector::class.simpleName
    }

    private var strategy: AbstractAnchorStrategy =
        WindowAnchorStrategy(WindowAnchorStrategy.ParametersImpl(window, anchor))

    private val originalWindowCallBack: Window.Callback = window.callback

    private var deactivated: AtomicBoolean = AtomicBoolean(false)

    private val layoutChangeListener = OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        Log.d(TAG, "OnLayoutChangeListener")
        relocate()
    }

    private val register: Pair<Runnable, Runnable> = Pair(Runnable {
        assert(!deactivated.get())
        anchor.addOnLayoutChangeListener(layoutChangeListener)
    }, Runnable {
        anchor.removeOnLayoutChangeListener(layoutChangeListener)
    })

    init {
        window.callback = object : WindowCallbackWrapper(window.callback) {
            override fun onContentChanged() {
                super.onContentChanged()
                relocate()
            }

            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                register.first.run()
            }

            override fun onDetachedFromWindow() {
                super.onDetachedFromWindow()
                register.second.run()
            }
        }
        relocate()
    }

    fun deactivate() {
        deactivated.set(true)
        window.callback = originalWindowCallBack
        register.second.run()
    }

    private fun relocate() {
        assert(!deactivated.get())

        if (!ViewCompat.isLaidOut(window.decorView)) {
            window.decorView.visibility = View.GONE
            debug("hide window because decor view isn't laid out")
        }
        debug("call relocate")
        window.decorView.doOnLayout {
            window.attributes = strategy.apply(window.attributes)
            debug("call relocate attrs on doOnLayout")
            if (!window.decorView.isVisible) {
                window.decorView.visibility = View.VISIBLE
            }
        }
    }

    private fun debug(message: String) {
        if (DEBUG) {
            Log.d(TAG, message)
        }
    }
}