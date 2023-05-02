package com.github.heesung6701.quokkaui.touchdelegate

import android.graphics.Rect
import android.view.View
import android.widget.Button
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TouchDelegateSetTest {
    companion object {
        private const val EVENT_CLICKED_BTN1 = "event clicked btn 1"
        private const val EVENT_CLICKED_BTN2 = "event clicked btn 2"
    }

    @get:Rule
    val activityScenarioRule = activityScenarioRule<TouchDelegateSetTestActivity>()

    private lateinit var activity: TouchDelegateSetTestActivity

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val uiDevice = UiDevice.getInstance(instrumentation)

    private val eventList = mutableListOf<String>()

    @Before
    fun setup() {
        activityScenarioRule.scenario.onActivity {
            activity = it
            val btn1 =
                activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn1)
            btn1.setOnClickListener {
                eventList.add(EVENT_CLICKED_BTN1)
            }
            val btn2 =
                activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn2)
            btn2.setOnClickListener {
                eventList.add(EVENT_CLICKED_BTN2)
            }
        }
    }

    @Test
    fun test_none() {
        Assert.assertTrue(true)
    }

    @Test
    fun test_clickButton1_adjacent() {
        val btn1 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn1)
        val btn1Bounds = btn1.getBounds()

        val boundsAdjacent = TouchDelegateSet.Builder.Insets.create(1)
        clickBoundWithInsetsTest(btn1Bounds,
            boundsAdjacent,
            {
                eventList.clear()
            },
            {
                Assert.assertEquals(
                    "Click (${it.first}, ${it.second}) / bounds of btn1 : $btn1Bounds",
                    1,
                    eventList.size
                )
                Assert.assertEquals(EVENT_CLICKED_BTN1, eventList[0])
            })
    }

    @Test
    fun test_clickButton1_inbounds() {
        val btn1 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn1)
        val btn1Bounds = btn1.getBounds()
        val expandedPixel = activity.getExpandedPx()

        val inBoundInsets = TouchDelegateSet.Builder.Insets.create(expandedPixel)
        clickBoundWithInsetsTest(btn1Bounds,
            inBoundInsets,
            {
                eventList.clear()
            },
            {
                Assert.assertEquals(
                    "Click (${it.first}, ${it.second}) / bounds of btn1 : $btn1Bounds",
                    1,
                    eventList.size
                )
                Assert.assertEquals(EVENT_CLICKED_BTN1, eventList[0])
            })
    }

    @Test
    fun test_clickButton1_outbounds() {
        val btn1 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn1)
        val btn1Bounds = btn1.getBounds()
        val expandedPixel = activity.getExpandedPx()

        val outBoundInsets = TouchDelegateSet.Builder.Insets.create(expandedPixel + 1)
        clickBoundWithInsetsTest(btn1Bounds,
            outBoundInsets,
            {
                eventList.clear()
            },
            {
                Assert.assertFalse(
                    "Click (${it.first}, ${it.second}) / bounds of btn1 : $btn1Bounds",
                    eventList.contains(EVENT_CLICKED_BTN1)
                )
            })
    }

    @Test
    fun test_clickButton2_adjacent() {
        val btn2 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn2)
        val btn2Bounds = btn2.getBounds()

        val boundsAdjacent = TouchDelegateSet.Builder.Insets.create(1)
        clickBoundWithInsetsTest(btn2Bounds,
            boundsAdjacent,
            {
                eventList.clear()
            },
            {
                Assert.assertEquals(
                    "Click (${it.first}, ${it.second}) / bounds of btn2 : $btn2Bounds",
                    1,
                    eventList.size
                )
                Assert.assertEquals(EVENT_CLICKED_BTN2, eventList[0])
            })
    }


    @Test
    fun test_clickButton2_inbounds() {
        val btn2 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn2)
        val btn2Bounds = btn2.getBounds()
        val expandedPixel = activity.getExpandedPx()

        clickBoundWithInsetsTest(btn2Bounds,
            TouchDelegateSet.Builder.Insets.create(expandedPixel),
            {
                eventList.clear()
            },
            {
                Assert.assertEquals(
                    "Click (${it.first}, ${it.second}) / bounds of btn2 : $btn2Bounds",
                    1,
                    eventList.size
                )
                Assert.assertEquals(EVENT_CLICKED_BTN2, eventList[0])
            })
    }


    @Test
    fun test_clickButton2_outbounds() {
        val btn2 =
            activity.findViewById<Button>(com.github.heesung6701.quokkaui.touchdelegate.test.R.id.btn2)
        val btn2Bounds = btn2.getBounds()
        val expandedPixel = activity.getExpandedPx()

        val outBoundInsets = TouchDelegateSet.Builder.Insets.create(expandedPixel + 1)
        clickBoundWithInsetsTest(btn2Bounds,
            outBoundInsets,
            {
                eventList.clear()
            },
            {
                Assert.assertFalse(
                    "Click (${it.first}, ${it.second}) / bounds of btn2 : $btn2Bounds",
                    eventList.contains(EVENT_CLICKED_BTN2)
                )
            })
    }

    private fun View.getBounds(): Rect {
        return Rect().apply {
            this@getBounds.getGlobalVisibleRect(this)
        }
    }

    private fun clickBoundWithInsetsTest(
        bound: Rect,
        insets: TouchDelegateSet.Builder.Insets,
        onBefore: () -> Unit,
        onAfter: (Pair<Int, Int>) -> Unit
    ) {
        val top = bound.top - insets.top
        val bottom = bound.bottom - 1 + insets.bottom
        val left = bound.left - insets.left
        val right = bound.right - 1 + insets.right
        listOf(
            Pair(left, top),
            Pair(bound.centerX(), top),
            Pair(right, top),
            Pair(left, bound.centerY()),
            Pair(right, bound.centerY()),
            Pair(left, bottom),
            Pair(bound.centerX(), bottom),
            Pair(right, bottom),
        ).forEach {
            onBefore()
            uiDevice.click(it.first, it.second)
            uiDevice.waitForIdle()
            onAfter(it)
        }
    }
}