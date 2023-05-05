package com.github.heesung6701.quokkaui.anchor.window.strategy

import android.view.WindowManager

object NoneAnchorStrategy : AbstractAnchorStrategy() {
    override fun reducer(): WindowManager.LayoutParams.() -> WindowManager.LayoutParams {
        return {
            this
        }
    }
}