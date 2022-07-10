package com.quokkaman.quokkatouchdelegate

import android.graphics.Rect
import android.graphics.Region
import android.os.Build
import android.util.ArrayMap
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi

class TouchDelegateSet(anchorView: View) : TouchDelegate(Rect(), anchorView) {
    private val touchDelegateList = ArrayList<CapturedTouchDelegate>();

    fun addTouchDelegate(touchDelegate: CapturedTouchDelegate) {
        touchDelegateList.add(touchDelegate)
    }

    fun removeTouchDelegate(touchDelegate: CapturedTouchDelegate): Boolean =
        touchDelegateList.remove(touchDelegate)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return touchDelegateList.fold(false) { acc: Boolean, cur: CapturedTouchDelegate ->
            acc or cur.onTouchEvent(event)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onTouchExplorationHoverEvent(event: MotionEvent): Boolean {
        return touchDelegateList.fold(false) { acc: Boolean, cur: CapturedTouchDelegate ->
            acc or cur.onTouchExplorationHoverEvent(event)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getTouchDelegateInfo(): AccessibilityNodeInfo.TouchDelegateInfo {
        val targetMap: ArrayMap<Region, View> = ArrayMap(touchDelegateList.size)
        touchDelegateList.forEach {
            it.apply {
                targetMap[Region(bounds)] = delegateView
            }
        }
        return AccessibilityNodeInfo.TouchDelegateInfo(targetMap)
    }

    class CapturedTouchDelegate(val bounds: Rect, val delegateView: View) : TouchDelegate(bounds, delegateView)
}