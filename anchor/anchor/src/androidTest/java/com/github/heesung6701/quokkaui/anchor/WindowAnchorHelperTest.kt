package com.github.heesung6701.quokkaui.anchor

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WindowAnchorHelperTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<ButtonOnBorderTestActivity>()

    private lateinit var activity: ButtonOnBorderTestActivity

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
    fun testStatic() {
        activity.buttonsList.forEach {
            it.performClick()
        }
    }
}