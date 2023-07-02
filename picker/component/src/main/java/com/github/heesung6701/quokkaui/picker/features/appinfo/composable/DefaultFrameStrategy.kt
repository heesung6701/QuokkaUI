package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.icon.IconFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.left.LeftFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.title.TitleFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.widget.WidgetFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AllSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType

open class DefaultFrameStrategy : FrameStrategy {
    override val leftFrameList: List<ComposableFrame> = LeftFrame.values().toList()
    override val iconFrameList: List<ComposableFrame> = IconFrame.values().toList()
    override val titleFrameList: List<ComposableFrame> = TitleFrame.values().toList()
    override val widgetFrameList: List<ComposableFrame> = WidgetFrame.values().toList()

    override fun selectComposableType(viewModel: ViewModel): ComposableType {
        return when (viewModel) {
            is AllSwitchViewModel -> {
                ComposableTypeSet.AllSwitch
            }

            is AppInfoSwitchViewModel -> {
                ComposableTypeSet.SwitchPreference
            }

            is AppInfoSubTitleViewModel -> {
                ComposableTypeSet.TwoTextLine
            }

            is AppInfoViewModel -> {
                ComposableTypeSet.SingleTextLine
            }
        }
    }
}