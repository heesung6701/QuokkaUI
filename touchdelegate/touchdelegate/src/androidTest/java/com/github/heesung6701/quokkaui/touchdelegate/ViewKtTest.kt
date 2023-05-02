package com.github.heesung6701.quokkaui.touchdelegate

import android.graphics.Rect
import android.view.View
import android.widget.Button
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.heesung6701.quokkaui.touchdelegate.ViewKt.calculateBounds
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewKtTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<TouchDelegateSetTestActivity>()

    private lateinit var activity: TouchDelegateSetTestActivity

    @Before
    fun setup() {
        activityScenarioRule.scenario.onActivity {
            activity = it
        }
    }

    @Test
    fun test_none() {
        Assert.assertTrue(true)
    }

    @Test
    fun testCalculateView() {
        val btn1 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn1)
        val btn2 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn2)

        val containerBounds = activity.container.getBounds()

        val calculatedBtn1Bound = activity.container.calculateBounds(btn1)
        val btn1Bounds = btn1.getBounds()
        Assert.assertEquals(btn1Bounds.top - containerBounds.top , calculatedBtn1Bound.top)
        Assert.assertEquals(btn1Bounds.left - containerBounds.left , calculatedBtn1Bound.left)
        Assert.assertEquals(btn1Bounds.bottom - containerBounds.top , calculatedBtn1Bound.bottom)
        Assert.assertEquals(btn1Bounds.right - containerBounds.left , calculatedBtn1Bound.right)

        val calculatedBtn2Bound = activity.container.calculateBounds(btn2)
        val btn2Bounds = btn2.getBounds()
        Assert.assertEquals(btn2Bounds.top - containerBounds.top , calculatedBtn2Bound.top)
        Assert.assertEquals(btn2Bounds.left - containerBounds.left , calculatedBtn2Bound.left)
        Assert.assertEquals(btn2Bounds.bottom - containerBounds.top , calculatedBtn2Bound.bottom)
        Assert.assertEquals(btn2Bounds.right - containerBounds.left , calculatedBtn2Bound.right)
    }

    private fun View.getBounds(): Rect {
        return Rect().apply {
            this@getBounds.getGlobalVisibleRect(this)
        }
    }
}