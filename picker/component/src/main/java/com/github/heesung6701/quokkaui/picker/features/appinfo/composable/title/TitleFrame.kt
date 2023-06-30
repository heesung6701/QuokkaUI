package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.title

import androidx.annotation.LayoutRes
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame

enum class TitleFrame(
    @LayoutRes
    override val layoutResId: Int,
    override val viewHolderClass: Class<out ComposableItemViewHolder>
) : ComposableFrame {
    SingleLine(
        R.layout.layout_app_info_title_single_line,
        ComposableTitleSingleLineViewHolder::class.java
    ),
    TwoLine(
        R.layout.layout_app_info_title_two_line,
        ComposableTitleTwoLineViewHolder::class.java
    );
}