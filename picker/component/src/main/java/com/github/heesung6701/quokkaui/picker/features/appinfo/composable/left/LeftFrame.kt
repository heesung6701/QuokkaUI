package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.left

import androidx.annotation.LayoutRes
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder

enum class LeftFrame(
    @LayoutRes
    override val layoutResId: Int,
    override val viewHolderClass: Class<out ComposableItemViewHolder>
) : ComposableFrame {
}