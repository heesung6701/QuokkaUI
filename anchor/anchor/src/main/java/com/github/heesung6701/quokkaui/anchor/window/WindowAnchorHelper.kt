package com.github.heesung6701.quokkaui.anchor.window

import android.view.View
import android.view.Window

class WindowAnchorHelper {

    private val hashMap = HashMap<Window, WindowAnchorConnector>()

    fun attach(window: Window, anchor: View) {
        hashMap[window]?.deactivate()
        hashMap[window] = WindowAnchorConnector(window, anchor)
    }

    fun detach(window: Window) {
        hashMap[window]?.let {
            it.deactivate()
            hashMap.remove(window)
        }
    }

    fun clearAll() {
        hashMap.values.forEach {
            it.deactivate()
        }
    }
}