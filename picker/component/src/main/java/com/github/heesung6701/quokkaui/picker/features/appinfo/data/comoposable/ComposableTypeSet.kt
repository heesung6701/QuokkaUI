package com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable

enum class ComposableTypeSet(
    override val leftFrame: ComposableFrame?,
    override val iconFrame: IconFrame,
    override val titleFrame: TitleFrame,
    override val rightFrame: ComposableFrame?
) : ComposableType {
    SingleTextLine(null, IconFrame.Icon, TitleFrame.SingleLine, null),
    TwoTextLine(null, IconFrame.Icon, TitleFrame.TwoLine, null),
    SwitchPreference(null, IconFrame.Icon, TitleFrame.TwoLine, RightFrame.Switch),
}
