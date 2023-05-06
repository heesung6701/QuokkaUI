package com.github.heesung6701.quokkaui.anchor

import android.graphics.Rect
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
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
class WindowAnchorConnectorTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<ButtonOnBorderTestActivity>()

    private lateinit var activity: ButtonOnBorderTestActivity
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val uiDevice = UiDevice.getInstance(instrumentation)
    private val rootRect = Rect()

    @Before
    fun setup() {
        activityScenarioRule.scenario.onActivity {
            activity = it
            activity.window.decorView.getGlobalVisibleRect(rootRect)
            ViewCompat.getRootWindowInsets(activity.window.decorView)
                ?.getInsets(WindowInsetsCompat.Type.systemBars())?.apply {
                    rootRect.top += top
                    rootRect.left += left
                    rootRect.right -= right
                    rootRect.bottom -= bottom
                }
        }
    }

    @Test
    fun test_none() {
        Assert.assertTrue(true)
    }

    @Test
    fun test_anchorToButton_TopLeft() {
        showDialogFromButton("btnTopLeft") { btnBounds, attr, _ ->
            Assert.assertEquals(btnBounds.left, attr.x)
            Assert.assertEquals(btnBounds.bottom - rootRect.top, attr.y)
        }
    }

    @Test
    fun test_anchorToButton_TopMid() {
        showDialogFromButton("btnTopMid") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.centerX() - dialogSize.first / 2, attr.x)
            Assert.assertEquals(btnBounds.bottom - rootRect.top, attr.y)
        }
    }

    @Test
    fun test_anchorToButton_TopRight() {
        showDialogFromButton("btnTopRight") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.right - dialogSize.first, attr.x)
            Assert.assertEquals(btnBounds.bottom - rootRect.top, attr.y)
        }
    }

    @Test
    fun test_anchorToButton_MidLeft() {
        showDialogFromButton("btnMidLeft") { btnBounds, attr, _ ->
            Assert.assertEquals(btnBounds.left, attr.x)
            Assert.assertEquals(btnBounds.bottom - rootRect.top, attr.y)
        }
    }

    @Test
    fun test_anchorToButton_Center() {
        showDialogFromButton("btnCenter") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.centerX() - dialogSize.first / 2, attr.x)
            Assert.assertEquals(btnBounds.bottom - rootRect.top, attr.y)
        }
    }

    @Test
    fun test_anchorToButton_MidRight() {
        showDialogFromButton("btnMidRight") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.right - dialogSize.first, attr.x)
            Assert.assertEquals(btnBounds.bottom - rootRect.top, attr.y)
        }
    }

    @Test
    fun test_anchorToButton_BottomLeft() {
        showDialogFromButton("btnBottomLeft") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.left, attr.x)
            Assert.assertEquals(btnBounds.top - rootRect.top, attr.y + dialogSize.second)
        }
    }

    @Test
    fun test_anchorToButton_BottomMid() {
        showDialogFromButton("btnBottomMid") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.centerX() - dialogSize.first / 2, attr.x)
            Assert.assertEquals(btnBounds.top - rootRect.top, attr.y + dialogSize.second)
        }
    }

    @Test
    fun test_anchorToButton_BottomRight() {
        showDialogFromButton("btnBottomRight") { btnBounds, attr, dialogSize ->
            Assert.assertEquals(btnBounds.right - dialogSize.first, attr.x)
            Assert.assertEquals(btnBounds.top - rootRect.top, attr.y + dialogSize.second)
        }
    }

    private fun showDialogFromButton(
        btnDescription: String,
        validator: (Rect, WindowManager.LayoutParams, Pair<Int, Int>) -> Unit
    ) {
        val btnBounds = Rect()
        onView(withContentDescription(btnDescription))
            .check { btn, _ ->
                btn.getGlobalVisibleRect(btnBounds)
            }
            .perform(click())
            .check { _, _ ->
                val lastDialog = activity.latestDialog
                Assert.assertNotNull(lastDialog)
                uiDevice.waitForIdle()
                val attr = lastDialog!!.window!!.attributes
                val decorView = lastDialog.window!!.peekDecorView()
                validator(
                    btnBounds,
                    attr,
                    Pair(
                        decorView.width,
                        decorView.height
                    )
                )
                lastDialog.dismiss()
            }
    }
}