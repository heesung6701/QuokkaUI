package com.github.heesung6701.quokkaui.touchdelegate

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.github.heesung6701.quokkaui.touchdelegate.test.R
import kotlin.math.roundToInt

class TouchDelegateSetTestActivity: Activity() {

    lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touchdelegateset_test_activity)

        container = findViewById(R.id.container)

        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)

        val expandedPx : Int = getExpandedPx()

        TouchDelegateSet.Builder(container)
            .add(btn1, TouchDelegateSet.Builder.Insets(expandedPx, expandedPx, expandedPx, expandedPx))
            .add(btn2, TouchDelegateSet.Builder.Insets(expandedPx, expandedPx, expandedPx, expandedPx))
            .done()
    }

    fun getExpandedPx(): Int = (12f /* dp */ * container.context.resources.displayMetrics.densityDpi / 160).roundToInt()
}