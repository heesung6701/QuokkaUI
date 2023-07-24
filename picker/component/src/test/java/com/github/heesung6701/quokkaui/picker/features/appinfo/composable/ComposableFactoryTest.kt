package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.graphics.drawable.Drawable
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AllSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

class ComposableFactoryTest {

    @Test
    fun test_getItemType_with_ViewModel() {
        val composableFactory = ComposableFactory(DefaultFrameStrategy(), ComposableBitConverter())
        val mockDrawable = mock(Drawable::class.java)

        listOf(
            AllSwitchViewModel(
                MutableStateFlow(false)
            ) to ComposableTypeSet.AllSwitch,
            AppInfoSwitchViewModel(
                key = AppInfo("", ""),
                appName = "",
                subTitle = "",
                appIcon = runBlocking { flow { emit(mockDrawable) } },
                onItemClicked = null,
                activateFlow = MutableStateFlow(false)
            ) to ComposableTypeSet.SwitchPreference,
            AppInfoSubTitleViewModel(
                key = AppInfo("", ""),
                onItemClicked = null,
                appName = "",
                subTitle = "",
                appIcon = runBlocking { flow { emit(mockDrawable) } },
            ) to ComposableTypeSet.TwoTextLine,
            AppInfoViewModel(
                key = AppInfo("", ""),
                onItemClicked = null,
                appName = "",
                appIcon = runBlocking { flow { emit(mockDrawable) } },
            ) to ComposableTypeSet.SingleTextLine
        ).forEach { (viewModel, composableSet) ->
            val actual = composableFactory.getItemType(viewModel)
            val expected = composableFactory.getItemType(composableSet)
            Assert.assertEquals(expected, actual)
        }
    }
}