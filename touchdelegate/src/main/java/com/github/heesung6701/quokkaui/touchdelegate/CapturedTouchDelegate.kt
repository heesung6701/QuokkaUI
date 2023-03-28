package com.github.heesung6701.quokkaui.touchdelegate

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View

class CapturedTouchDelegate(val bounds: Rect, val delegateView: View) :
    TouchDelegate(bounds, delegateView)