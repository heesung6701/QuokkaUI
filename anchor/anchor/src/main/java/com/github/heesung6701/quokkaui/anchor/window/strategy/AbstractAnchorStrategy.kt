package com.github.heesung6701.quokkaui.anchor.window.strategy

import android.view.WindowManager

abstract class AbstractAnchorStrategy {

    fun apply(
        windowParams: WindowManager.LayoutParams,
    ): WindowManager.LayoutParams {
        return with(windowParams, reducer())
    }

    abstract fun reducer(): WindowManager.LayoutParams.() -> WindowManager.LayoutParams
}