package com.github.heesung6701.quokkaui.catalog.common

import android.app.Activity
import android.view.View

fun Activity.findAllViews(filter: (View) -> Boolean) : List<View> {
    return ViewFinder(filter).findAllViews(this.window.peekDecorView())
}