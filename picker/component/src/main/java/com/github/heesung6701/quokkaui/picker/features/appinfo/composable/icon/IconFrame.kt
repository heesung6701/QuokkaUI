package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.icon

import androidx.annotation.LayoutRes
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder

enum class IconFrame(
    @LayoutRes
    override val layoutResId: Int,
    override val viewHolderClass: Class<out ComposableItemViewHolder>
) : ComposableFrame {
    Icon(R.layout.layout_app_info_icon, ComposableIconViewHolder::class.java);
}