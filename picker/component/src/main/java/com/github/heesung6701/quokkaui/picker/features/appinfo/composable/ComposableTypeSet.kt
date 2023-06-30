package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.icon.IconFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.title.TitleFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.widget.WidgetFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType

enum class ComposableTypeSet(
    override val leftFrame: ComposableFrame?,
    override val iconFrame: IconFrame?,
    override val titleFrame: TitleFrame,
    override val rightFrame: ComposableFrame?
) : ComposableType {
    SingleTextLine(null, IconFrame.Icon, TitleFrame.SingleLine, null),
    TwoTextLine(null, IconFrame.Icon, TitleFrame.TwoLine, null),
    SwitchPreference(null, IconFrame.Icon, TitleFrame.TwoLine, WidgetFrame.Switch),
    AllSwitch(null, null, TitleFrame.SingleLine, WidgetFrame.Switch),
}
