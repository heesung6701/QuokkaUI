package com.github.heesung6701.quokkaui.picker.widget

import android.content.Context
import androidx.core.app.ComponentActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModelFactory
import com.github.heesung6701.quokkaui.picker.features.di.Providers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComponentPickerViewTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<ComponentActivity>()

    @Before
    fun setup() {
    }

    @Test
    fun test_customProvider() {
        lateinit var activity: ComponentActivity
        activityScenarioRule.scenario.onActivity {
            it.setContentView(com.github.heesung6701.quokkaui.picker.test.R.layout.activity_component_picker_with_custom_provider)
            activity = it
        }
        val picker = activity.findViewById<ComponentPickerView>(com.github.heesung6701.quokkaui.picker.test.R.id.component_picker)
        Assert.assertTrue(picker.viewModelFactory is CustomViewModelFactory)
    }

    open class CustomProvider(context: Context): Providers(context) {

        override val viewModelFactory: ViewModelFactory by lazy { CustomViewModelFactory(context) }
    }

    class CustomViewModelFactory(context: Context): ViewModelFactory(context) {

    }
}