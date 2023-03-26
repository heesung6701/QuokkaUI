package com.github.heesung6701.quokkaui.touchdelegate

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewParent

class TouchDelegateSetBuilder(private val anchorView: View) {

    private val jobs: ArrayList<() -> TouchDelegateSet.CapturedTouchDelegate> = ArrayList()

    fun add(childView: View, insets: Insets? = null): TouchDelegateSetBuilder {
        jobs.add {
            val bounds = calculateBounds(childView)
            insets?.let {
                bounds.left -= insets.left
                bounds.top -= insets.top
                bounds.right += insets.right
                bounds.bottom += insets.bottom
            }
            Log.d("HSSS", bounds.toString())
            TouchDelegateSet.CapturedTouchDelegate(bounds, childView)
        }
        return this
    }

    fun done() {
        anchorView.post {
            val touchDelegateSet = TouchDelegateSet(anchorView)
            jobs.forEach {
                touchDelegateSet.addTouchDelegate(it.invoke())
            }
            anchorView.touchDelegate = touchDelegateSet
        }
    }

    private fun calculateBounds(view: View) : Rect {
        val bounds = Rect()
        view.getHitRect(bounds)
        var head: ViewParent = view.parent ?: return bounds
        while (head != anchorView) {
            if (head !is View) {
                throw RuntimeException("only allow child of anchor View")
            }
            val cur = head as View
            Rect().apply {
                cur.getHitRect(this)
                bounds.offset(-left, -top)
            }
            head = cur.parent ?: throw RuntimeException("only allow child of anchor View")
        }
        return bounds
    }

    public data class Insets(val left: Int, val top: Int, val right: Int, val bottom: Int)
}