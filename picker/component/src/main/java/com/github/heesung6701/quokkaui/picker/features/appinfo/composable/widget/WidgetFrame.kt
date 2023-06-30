package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.widget

import androidx.annotation.LayoutRes
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame

enum class WidgetFrame(
    @LayoutRes
    override val layoutResId: Int,
    override val viewHolderClass: Class<out ComposableItemViewHolder>
) : ComposableFrame {
    Switch(
        R.layout.layout_app_info_switch,
        ComposableSwitchViewHolder::class.java
    );
}