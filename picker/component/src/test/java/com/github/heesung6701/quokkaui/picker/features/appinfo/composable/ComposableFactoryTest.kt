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
    fun test_Companion_getLeftFrameId() {
        val actual = ComposableFactory.getLeftFrameId(0xabcd)
        Assert.assertEquals((0xa).toString(2), actual.toString(2))
    }

    @Test
    fun test_Companion_getIconFrameId() {
        val actual = ComposableFactory.getIconFrameId(0xabcd)
        Assert.assertEquals((0xb).toString(2), actual.toString(2))
    }

    @Test
    fun test_Companion_getTitleFrameId() {
        val actual = ComposableFactory.getTitleFrameId(0xabcd)
        Assert.assertEquals((0xc).toString(2), actual.toString(2))
    }

    @Test
    fun test_Companion_getWidgetFrameId() {
        val actual = ComposableFactory.getWidgetFrameId(0xabcd)
        Assert.assertEquals((0xd).toString(2), actual.toString(2))
    }

    @Test
    fun test_getItemType_with_ViewModel() {
        val composableFactory = ComposableFactory()
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

    @Test
    fun test_getItemType_with_composableType() {

        val composableFactory = ComposableFactory()

        listOf(
            ComposableTypeSet.AllSwitch to ((1 shl 4) + 1),
            ComposableTypeSet.SwitchPreference to ((1 shl 8) + (2 shl 4) + 1),
            ComposableTypeSet.TwoTextLine to ((1 shl 8) + (2 shl 4)),
            ComposableTypeSet.SingleTextLine to ((1 shl 8) + (1 shl 4))
        ).forEach { (composableType, expected) ->
            val actual = composableFactory.getItemType(composableType)

            Assert.assertEquals(
                expected.toString(2),
                actual.toString(2)
            )
        }
    }
}