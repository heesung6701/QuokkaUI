package com.github.heesung6701.quokkaui.touchdelegate

import android.graphics.Rect
import android.view.View
import android.view.ViewParent

object ViewKt {
    fun View.calculateBounds(childView: View) : Rect {
        val bounds = Rect()
        childView.getHitRect(bounds)
        var head: ViewParent = childView.parent ?: return bounds
        while (head != this) {
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

}