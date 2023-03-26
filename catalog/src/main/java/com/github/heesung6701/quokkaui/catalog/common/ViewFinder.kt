package com.github.heesung6701.quokkaui.catalog.common

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

class ViewFinder(private val filter: (View) -> Boolean) {

    fun findAllViews(view: View): List<View> {
        val ret = mutableListOf<View>()
        if (view is ViewGroup) {
            view.children.forEach {
                if (it is ViewGroup) {
                    ret.addAll(findAllViews(it))
                    return@forEach
                }
                if (filter.invoke(it)) {
                    ret.add(it)
                }
            }
        }
        return ret
    }
}
