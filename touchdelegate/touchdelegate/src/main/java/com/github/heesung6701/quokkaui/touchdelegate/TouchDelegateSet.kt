package com.github.heesung6701.quokkaui.touchdelegate

import android.graphics.Rect
import android.graphics.Region
import android.os.Build
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import com.github.heesung6701.quokkaui.touchdelegate.ViewKt.calculateBounds

class TouchDelegateSet(private val anchorView: View) : TouchDelegate(Rect(), anchorView) {
    private val touchDelegateSet = HashSet<CapturedTouchDelegate>()

    fun addTouchDelegate(touchDelegate: CapturedTouchDelegate) {
        touchDelegateSet.add(touchDelegate)
    }

    fun removeTouchDelegate(touchDelegate: CapturedTouchDelegate): Boolean =
        touchDelegateSet.remove(touchDelegate)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return touchDelegateSet.fold(false) { acc: Boolean, cur: CapturedTouchDelegate ->
            acc or cur.onTouchEvent(event)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onTouchExplorationHoverEvent(event: MotionEvent): Boolean {
        return false
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getTouchDelegateInfo(): AccessibilityNodeInfo.TouchDelegateInfo {
        return AccessibilityNodeInfo.TouchDelegateInfo(hashMapOf(Pair(Region(), anchorView)))
    }

    class Builder(private val anchorView: View) {

        private val jobs: ArrayList<() -> CapturedTouchDelegate> = ArrayList()

        fun add(childView: View, insets: Insets? = null): Builder {
            jobs.add {
                val bounds = anchorView.calculateBounds(childView)
                insets?.let {
                    bounds.left -= insets.left
                    bounds.top -= insets.top
                    bounds.right += insets.right
                    bounds.bottom += insets.bottom
                }
                CapturedTouchDelegate(bounds, childView)
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

        data class Insets(val left: Int, val top: Int, val right: Int, val bottom: Int) {
            companion object {
                fun create(size: Int) = Insets(size, size, size, size)
                fun create(horizontal: Int, vertical: Int) = Insets(horizontal, vertical, horizontal, vertical)
            }
        }
    }
}